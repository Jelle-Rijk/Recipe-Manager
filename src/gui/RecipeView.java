package gui;

import java.util.List;
import java.util.Map;

import dto.RecipeDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecipeView extends VBox {

	private Label title;
	private Text description;
	private VBox ingredients;
	private VBox instructions;
	private RecipeDTO recipe;

	private final static Insets PADDING = new Insets(10);

	public RecipeView(RecipeDTO recipe) {
		this.recipe = recipe;
		buildGUI();
	}

	/*
	 * GUI BUILDING
	 */

	private void buildGUI() {
		setPadding(PADDING);

		HBox header = buildHeader();
		VBox ingredients = buildIngredients(recipe.ingredientAmounts());
		VBox instructions = buildInstructions(recipe.instructions());
		FlowPane fPane = new FlowPane();
		fPane.getChildren().addAll(ingredients, instructions);

		getChildren().addAll(header, fPane);
	}

	private HBox buildHeader() {
		HBox header = new HBox();
		header.setSpacing(20);

		// TITLE, DESCRIPTION, TIME
		Label title = buildTitle();
		VBox infoHeader = new VBox(title);

		if (!recipe.description().equals("N/A")) {
			description = buildDescription(recipe.description());
			infoHeader.getChildren().add(description);
		}
		infoHeader.getChildren().add(buildCookingTime());

		// IMAGE
		Label image = new Label("IMAGE");
		image.setStyle("-fx-font-size: 40");

		header.getChildren().addAll(infoHeader, image);
		return header;
	}

	private Label buildTitle() {
		Label title = new Label(recipe.name());
		title.setStyle("-fx-font-size: 36; -fx-font-style: italic;");
		return title;
	}

	private Text buildDescription(String description) {
		return new Text(description);
	}

	private Label buildCookingTime() {
		int timeInMinutes = recipe.cookingTime();
		String time = String.format("%d mins.", timeInMinutes % 60);
		if (timeInMinutes > 60)
			time = String.format("%d h. and %s", timeInMinutes / 60, time);
		Label timeLabel = new Label(time);
		timeLabel.setPadding(new Insets(5, 5, 0, 5));
		return timeLabel;
	}

	private VBox buildIngredients(Map<String, Integer> map) {
		VBox ingredients = new VBox();

		Label ingredientsTitle = new Label("Ingredients");
		ingredientsTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		ingredients.getChildren().add(ingredientsTitle);

		for (String key : map.keySet()) {
			String ingredient = String.format("%d %s", map.get(key), key);
			ingredients.getChildren().add(new Label(ingredient));
		}
		ingredients.setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);

		ingredients.setPadding(PADDING);
		return ingredients;
	}

	private VBox buildInstructions(List<String> list) {
		VBox instructions = new VBox();
		instructions.setMaxWidth(250);
		instructions.setPadding(PADDING);

		Label instructionsTitle = new Label("Steps");
		instructionsTitle.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
		instructions.getChildren().add(instructionsTitle);

		int stepNumber = 1;
		for (String instruction : list) {
			Label text = new Label(String.format("%d) %s", stepNumber, instruction));
			text.setPrefWidth(instructions.getMaxWidth());
			text.setPadding(new Insets(5));
			text.setWrapText(true);
			instructions.getChildren().add(text);
			stepNumber++;
		}

		return instructions;
	}

}
