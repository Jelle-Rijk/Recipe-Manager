package domain.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Recipe {
	private final String name;
	private Optional<String> description;
	private int cookingTime; // in minutes
	private Map<String, Integer> ingredientAmounts;
	private List<String> instructions;

	/*
	 * CONSTRUCTORS
	 */
	public Recipe(String name, String description, int cookingTime, Map<String, Integer> ingredientAmounts,
			List<String> instructions) {
		validateName(name);
		this.name = name;
		setDescription(description);
		setCookingTime(cookingTime);
		setIngredientAmounts(ingredientAmounts);
		setInstructions(instructions);
	}

	public Recipe(String name) {
		this(name, null, 0, new HashMap<String, Integer>(), new ArrayList<String>());
	}

	/*
	 * PRIVATE METHODS
	 */
	private void validateName(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException("Every recipe needs a name.");
	}

	/*
	 * OVERRIDES - OBJECT
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Recipe r = (Recipe) o;
		return getName().equals(r.getName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}

	/*
	 * GETTERS - SETTERS
	 */
	public Optional<String> getDescription() {
		return description;
	}

	public final void setDescription(String description) {
//		if (description.isBlank())
//			description = null;
		this.description = Optional.ofNullable(description);
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public final void setCookingTime(int cookingTime) {
		if (cookingTime < 0)
			throw new IllegalArgumentException("Cooking time cannot be negative.");
		this.cookingTime = cookingTime;
	}

	public Map<String, Integer> getIngredientAmounts() {
		return ingredientAmounts;
	}

	public final void setIngredientAmounts(Map<String, Integer> ingredients) {
		this.ingredientAmounts = ingredients;
	}

	public List<String> getInstructions() {
		return instructions;
	}

	public final void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}

	public String getName() {
		return name;
	}

}
