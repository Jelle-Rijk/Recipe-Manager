package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class RecipeRepo {
	private List<Recipe> recipes;

	public RecipeRepo() {
		recipes = new ArrayList<Recipe>();
	}

	/*
	 * CREATE
	 */
	public void addRecipe(Recipe r) {
		recipes.add(r);
	}

	/*
	 * READ
	 */
	/**
	 * Returns Recipe object with given name
	 * 
	 * @param name The name of the Recipe
	 * @return The Recipe object with the corresponding name
	 * @throws NoSuchElementException when no recipe was found
	 */
	public Recipe getRecipe(String name) throws IllegalArgumentException {
		try {
			return recipes.stream().filter(recipe -> recipe.getName().equals(name)).findFirst().orElseThrow();
		} catch (NoSuchElementException nsee) {
			throw new IllegalArgumentException("Could not find a recipe with that name.");
		}
	}

	/*
	 * UPDATE
	 */

	/*
	 * DELETE
	 */

}
