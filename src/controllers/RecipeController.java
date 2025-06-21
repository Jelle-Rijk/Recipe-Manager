package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.recipes.Recipe;
import domain.recipes.RecipeRepo;
import dto.RecipeDTO;

public class RecipeController {
	private Recipe currentRecipe;
	private RecipeRepo rRepo;

	public RecipeController() {
		rRepo = new RecipeRepo();
		setDefaults();
	}

	private static String DEFAULT_NAME = "Pasta Carbonara";
	private static String DEFAULT_DESCRIPTION = "Homecooked Italian deliciousness";
	private static int DEFAULT_COOKING_TIME = 90;
	private static Map<String, Integer> DEFAULT_INGREDIENTS;
	private static List<String> DEFAULT_INSTRUCTIONS;

	private void setDefaults() {
		DEFAULT_INGREDIENTS = new HashMap<String, Integer>();
		DEFAULT_INGREDIENTS.put("Bacon", 300);
		DEFAULT_INGREDIENTS.put("Spaghetti", 200);
		DEFAULT_INGREDIENTS.put("Cheese", 100);
		DEFAULT_INGREDIENTS.put("Egg", 1);

		DEFAULT_INSTRUCTIONS = new ArrayList<String>();
		DEFAULT_INSTRUCTIONS.add("Cook the pasta and bake the bacon.");
		DEFAULT_INSTRUCTIONS.add("Grate the cheese and mix it with the egg.");
		DEFAULT_INSTRUCTIONS.add("Add pasta to bacon and pour egg on it.");
		DEFAULT_INSTRUCTIONS
				.add("Whisk thoroughly and pour some pasta water over the spaghetti until you get a smooth sauce.");
		addRecipe(new RecipeDTO(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_COOKING_TIME, DEFAULT_INGREDIENTS,
				DEFAULT_INSTRUCTIONS));
	}

	public void addRecipe(RecipeDTO dto) {
		rRepo.addRecipe(RecipeDTO.convertToRecipe(dto));
	}

	/*
	 * READ
	 */
	public RecipeDTO getRecipe(String name) {
		return RecipeDTO.convertToDTO(rRepo.getRecipe(name));
	}

}
