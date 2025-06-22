package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domain.recipes.Recipe;

class RecipeTest {
	private Recipe r;

	private static String DEFAULT_NAME = "Pasta Carbonara";
	private static String DEFAULT_DESCRIPTION = "Homecooked Italian deliciousness";
	private static int DEFAULT_COOKING_TIME = 30;
	private static Map<String, Integer> DEFAULT_INGREDIENTS;
	private static List<String> DEFAULT_INSTRUCTIONS;

	@BeforeAll
	static void setDefaults() {
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
	}

	@BeforeEach
	void setUp() throws Exception {
		r = new Recipe(DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_COOKING_TIME, DEFAULT_INGREDIENTS,
				DEFAULT_INSTRUCTIONS);
	}

	/*
	 * CONSTRUCTORS
	 */
	@Test
	void test_defaultRecipe_ReturnsCorrectValues() {
		assertEquals(DEFAULT_NAME, r.getName());
		assertEquals(DEFAULT_DESCRIPTION, r.getDescription());
		assertEquals(DEFAULT_COOKING_TIME, r.getCookingTime());
		assertEquals(DEFAULT_INGREDIENTS, r.getIngredientAmounts());
		assertEquals(DEFAULT_INSTRUCTIONS, r.getInstructions());
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = { "", " ", "\t", "\n" })
	void test_invalidName_throwsException(String name) {
		assertThrows(IllegalArgumentException.class, () -> new Recipe(name));
	}

}
