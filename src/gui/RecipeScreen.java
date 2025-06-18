package gui;

import java.util.List;
import java.util.Map;

import dto.RecipeDTO;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class RecipeScreen extends FlowPane {

	private Label title;
	private Text description;
	private Label cookingTime;
	private ListView<String> ingredients;
	private ListView<String> instructions;

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

		getChildren().addAll(cookingTime, ingredients, instructions);
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

	private ListView<String> buildInstructions(List<String> list) {
		ListView<String> lv = new ListView<String>();
		int stepNumber = 1;
		for (String instruction : list) {
			String step = String.format("%d) %s", stepNumber, instruction);
			lv.getItems().add(step);
			stepNumber++;
		}
		return lv;
	}

}
