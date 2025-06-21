package dto;

import domain.Ingredient;

public record IngredientDTO(String name) {
	public static IngredientDTO convertToDTO(Ingredient i) {
		return new IngredientDTO(i.getName());
	}

	public static Ingredient convertToRecipe(IngredientDTO dto) {
		return new Ingredient(dto.name());
	}
}
