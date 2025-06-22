package persistence.recipes;

import java.util.List;

import domain.recipes.Recipe;

public interface RecipeService {
	public List<Recipe> loadRecipes();

	public void saveRecipe(Recipe r);

	public void deleteRecipe(Recipe r);

}
