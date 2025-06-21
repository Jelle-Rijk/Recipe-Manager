package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.recipes.Recipe;
import domain.recipes.RecipeRepo;
import dto.RecipeDTO;
import utils.Publisher;
import utils.Subscriber;

public class RecipeController implements Publisher {
	private List<Subscriber> currentRecipeSubscribers;
	private Recipe currentRecipe;
	private RecipeRepo rRepo;

	public RecipeController() {
		currentRecipeSubscribers = new ArrayList<Subscriber>();
		rRepo = new RecipeRepo();
		setDefaults();
		setCurrentRecipe("Pasta Carbonara");
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

	public void removeRecipe(RecipeDTO dto) {
		rRepo.removeRecipe(RecipeDTO.convertToRecipe(dto));
	}

	/**
	 * Sets the currently selected recipe.
	 * 
	 * @param dto The RecipeDTO for the Recipe to set.
	 */
	public void setCurrentRecipe(RecipeDTO dto) {
		setCurrentRecipe(dto.name());
	}

	/**
	 * Sets the currently selected recipe.
	 * 
	 * @param name The name property of the Recipe to set.
	 */
	public void setCurrentRecipe(String name) {
		try {
			Recipe r = rRepo.getRecipe(name);
			currentRecipe = r;
			notifySubscribers();
		} catch (IllegalArgumentException iae) {
			System.err.println("Could not set the current recipe. No recipe found for name.");
		}

	}

	public RecipeDTO getCurrentRecipe() {
		return RecipeDTO.convertToDTO(currentRecipe);
	}

	/**
	 * Removes oldRecipe and adds newRecipe.
	 * 
	 * @param oldRecipe
	 * @param newRecipe
	 */
	public void changeRecipe(RecipeDTO oldRecipe, RecipeDTO newRecipe) {
		rRepo.removeRecipe(RecipeDTO.convertToRecipe(oldRecipe));
		rRepo.addRecipe(RecipeDTO.convertToRecipe(newRecipe));
		setCurrentRecipe(newRecipe);
	}

	/*
	 * READ
	 */
	public RecipeDTO getRecipe(String name) {
		return RecipeDTO.convertToDTO(rRepo.getRecipe(name));
	}

	/*
	 * PUBLISHER INTERFACE
	 */

	@Override
	public void subscribe(Subscriber sub) {
		currentRecipeSubscribers.add(sub);
	}

	@Override
	public void unsubscribe(Subscriber sub) {
		currentRecipeSubscribers.remove(sub);
	}

	@Override
	public void notifySubscribers() {
		currentRecipeSubscribers.forEach(sub -> sub.update());
	}

}
