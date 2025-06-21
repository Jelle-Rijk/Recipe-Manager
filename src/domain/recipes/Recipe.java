package domain.recipes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Recipe {
	public String getName();

	public Optional<String> getDescription();

	public int getCookingTime();

	public Map<String, Integer> getIngredientAmounts();

	public List<String> getInstructions();
}
