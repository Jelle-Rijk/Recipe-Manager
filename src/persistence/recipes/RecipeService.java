package persistence.recipes;

import java.util.Collection;

import domain.recipes.RecipeImpl;

public interface RecipeService {
	public Collection<RecipeImpl> loadRecipes();

	public void saveRecipe(RecipeImpl r);

	public void deleteRecipe(RecipeImpl r);

}
