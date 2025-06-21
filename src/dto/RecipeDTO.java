package dto;

import java.util.List;
import java.util.Map;

import domain.recipes.Recipe;

public record RecipeDTO(String name, String description, int cookingTime, Map<String, Integer> ingredientAmounts,
		List<String> instructions) {

	public static RecipeDTO convertToDTO(Recipe r) {
		return new RecipeDTO(r.getName(), r.getDescription().orElse("N/A"), r.getCookingTime(),
				r.getIngredientAmounts(), r.getInstructions());
	}

	public static Recipe convertToRecipe(RecipeDTO dto) {
		return new Recipe(dto.name(), dto.description(), dto.cookingTime(), dto.ingredientAmounts(),
				dto.instructions());
	}
}
