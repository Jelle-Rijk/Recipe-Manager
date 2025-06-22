package domain.recipes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import enums.ObserverEvent;
import persistence.recipes.RecipeService;
import persistence.recipes.RecipeServiceImpl;
import utils.Publisher;
import utils.Subscriber;

public class RecipeRepo implements Publisher {
	private final RecipeService service;
	private List<Recipe> recipes;

	private List<Subscriber> subscribers;

	public RecipeRepo() {
		service = new RecipeServiceImpl();
		recipes = service.loadRecipes();
		subscribers = new ArrayList<Subscriber>();
	}

	/*
	 * CREATE
	 */
	public void addRecipe(Recipe r) {
		recipes.add(r);
		service.saveRecipe(r);
		notifySubscribers();
	}

	/*
	 * READ
	 */
	/**
	 * Returns Recipe object with given name
	 * 
	 * @param name The name of the Recipe
	 * @return The Recipe object with the corresponding name
	 * @throws IllegalArgumentException when no recipe was found
	 */
	public Recipe getRecipe(String name) throws IllegalArgumentException {
		try {
			return recipes.stream().filter(recipe -> recipe.getName().equals(name)).findFirst().orElseThrow();
		} catch (NoSuchElementException nsee) {
			throw new IllegalArgumentException("Could not find a recipe with that name.");
		}
	}

	/**
	 * Gets all recipes in a collection.
	 * 
	 * @return A collection of all recipes in the repository.
	 */
	public Collection<Recipe> getRecipes() {
		return recipes;
	}

	/*
	 * UPDATE
	 */

	/*
	 * DELETE
	 */
	public void removeRecipe(Recipe r) {
		recipes.remove(r);
		service.deleteRecipe(r);
		notifySubscribers();
	}

	/*
	 * PUBLISHER INTERFACE
	 */
	@Override
	public void subscribe(Subscriber sub, ObserverEvent eventType) {
		subscribers.add(sub);
	}

	@Override
	public void unsubscribe(Subscriber sub, ObserverEvent eventType) {
		subscribers.remove(sub);
	}

	@Override
	public void notifySubscribers(ObserverEvent eventType) {
		subscribers.forEach(sub -> sub.update(ObserverEvent.LIST_CHANGE));
	}

}
