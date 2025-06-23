package gui;

import controllers.RecipeController;
import dto.RecipeDTO;
import enums.ObserverEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.Subscriber;

public class RecipeView extends VBox implements Subscriber {
	private final RecipeController rc;

	private Label title;
	private Text description;
	private Label cookingTime;
	private VBox ingredients;
	private VBox instructions;

	private final static Insets PADDING = new Insets(10);

	public RecipeView(RecipeController rc) {
		if (rc == null)
			throw new IllegalArgumentException("RecipeView needs a RecipeController");
		this.rc = rc;
		rc.subscribe(this);
		buildGUI();
	}

	/*
	 * GUI BUILDING
	 */

	private void buildGUI() {
		setPadding(PADDING);

		HBox header = buildHeader();
		ingredients = buildIngredients();
		instructions = buildInstructions();
		FlowPane fPane = new FlowPane();
		fPane.getChildren().addAll(ingredients, instructions);

		getChildren().addAll(header, fPane);

		if (rc.getCurrentRecipe() != null)
			update();
	}

	private HBox buildHeader() {
		HBox header = new HBox();
		header.setSpacing(20);

		// TITLE, DESCRIPTION, TIME
		VBox infoHeader = new VBox();
		title = buildTitle();
		description = new Text();
		cookingTime = new Label();
		cookingTime.setPadding(new Insets(5, 5, 0, 5));
		infoHeader.getChildren().addAll(title, description, cookingTime);

		// IMAGE
		Label image = new Label("IMAGE");
		image.setStyle("-fx-font-size: 40");

		header.getChildren().addAll(infoHeader, image);
		return header;
	}

	private Label buildTitle() {
		Label title = new Label();
		title.setStyle("-fx-font-size: 36; -fx-font-style: italic;");
		return title;
	}

	private String formatCookingTime(int timeInMinutes) {
		String time = String.format("%d mins.", timeInMinutes % 60);
		if (timeInMinutes > 60)
			time = String.format("%d h. and %s", timeInMinutes / 60, time);
		return time;
	}

	private VBox buildIngredients() {
		VBox ingredients = new VBox();

		Label ingredientsTitle = new Label("Ingredients");
		ingredientsTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		ingredients.getChildren().add(ingredientsTitle);
		ingredients.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		ingredients.setPadding(PADDING);
		return ingredients;
	}

	private VBox buildInstructions() {
		VBox instructions = new VBox();
		instructions.setMaxWidth(250);
		instructions.setPadding(PADDING);

		Label instructionsTitle = new Label("Steps");
		instructionsTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		instructions.getChildren().add(instructionsTitle);

		return instructions;
	}

	@Override
	public void update(ObserverEvent eventType) {
		RecipeDTO recipe = rc.getCurrentRecipe();

		title.setText(recipe.name());
		description.setText(recipe.description());
		cookingTime.setText(formatCookingTime(recipe.cookingTime()));

		ingredients.getChildren().clear();
		for (String key : recipe.ingredientAmounts().keySet()) {
			String ingredient = String.format("%d %s", recipe.ingredientAmounts().get(key), key);
			ingredients.getChildren().add(new Label(ingredient));
		}

		instructions.getChildren().clear();
		int stepNumber = 1;
		for (String instruction : recipe.instructions()) {
			Label text = new Label(String.format("%d) %s", stepNumber, instruction));
			text.setPrefWidth(instructions.getMaxWidth());
			text.setPadding(new Insets(5));
			text.setWrapText(true);
			instructions.getChildren().add(text);
			stepNumber++;
		}
	}
}
