package persistence.recipes;

import java.util.Collection;

import domain.recipes.Recipe;

public interface RecipeService {
	public Collection<Recipe> loadRecipes();

	public void saveRecipe(Recipe r);

	public void deleteRecipe(Recipe r);

}
