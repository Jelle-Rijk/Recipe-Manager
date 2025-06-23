package dto;

import java.util.List;
import java.util.Map;

import domain.recipes.Recipe;

public record RecipeDTO(String name, String description, int cookingTime, Map<String, Integer> ingredientAmounts,
		List<String> instructions) {

	public static RecipeDTO convertToDTO(Recipe r) {
		String description = r.getDescription();
		if (description == null || description.isBlank())
			description = "N/A";

		return new RecipeDTO(r.getName(), description, r.getCookingTime(), r.getIngredientAmounts(),
				r.getInstructions());
	}

	public static Recipe convertToRecipe(RecipeDTO dto) {
		return new Recipe(dto.name(), dto.description(), dto.cookingTime(), dto.ingredientAmounts(),
				dto.instructions());
	}
}
