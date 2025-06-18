package gui;

import java.util.List;
import java.util.Map;

import dto.RecipeDTO;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class RecipeScreen extends VBox {

	private Label title;
	private Text description;
	private Label cookingTime;
	private ListView<String> ingredients;
	private ListView<Label> instructions;

	public RecipeScreen(RecipeDTO recipe) {
		buildGUI(recipe);
	}

	/*
	 * GUI BUILDING
	 */

	private void buildGUI(RecipeDTO recipe) {
		title = buildTitle(recipe.name());
		getChildren().add(title);

		if (!recipe.description().equals("N/A")) {
			description = buildDescription(recipe.description());
			getChildren().add(description);
		}

		cookingTime = buildCookingTime(recipe.cookingTime());

		ingredients = buildIngredients(recipe.ingredientAmounts());
		instructions = buildInstructions(recipe.instructions());
		FlowPane fPane = new FlowPane();
		fPane.getChildren().addAll(ingredients, instructions);

		getChildren().addAll(cookingTime, fPane);
	}

	private Label buildTitle(String name) {
		return new Label(name);
	}

	private Text buildDescription(String description) {
		return new Text(description);
	}

	private Label buildCookingTime(int timeInMinutes) {
		return new Label(Integer.toString(timeInMinutes));
	}

	private ListView<String> buildIngredients(Map<String, Integer> map) {
		ListView<String> lv = new ListView<String>();
		for (String key : map.keySet()) {
			String ingredient = String.format("%s - %d", key, map.get(key));
			lv.getItems().add(ingredient);
		}
		return lv;
	}

	private ListView<Label> buildInstructions(List<String> list) {
		ListView<Label> lv = new ListView<Label>();
		lv.setPrefWidth(250);
		int stepNumber = 1;
		for (String instruction : list) {
			Label text = new Label(String.format("%d) %s", stepNumber, instruction));
			text.setPrefWidth(lv.getPrefWidth());
			text.setPadding(new Insets(5));
			text.setWrapText(true);
			lv.getItems().add(text);
			stepNumber++;
		}

		return lv;
	}

}
