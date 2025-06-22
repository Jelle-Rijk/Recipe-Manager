package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import domain.recipes.Recipe;
import domain.recipes.RecipeRepo;
import dto.RecipeDTO;
import enums.ObserverEvent;
import utils.Publisher;
import utils.Subscriber;

public class RecipeController implements Publisher {
	private List<Subscriber> currentRecipeSubscribers;
	private Recipe currentRecipe;
	private RecipeRepo rRepo;

	public RecipeController() {
		currentRecipeSubscribers = new ArrayList<Subscriber>();
		rRepo = new RecipeRepo();
	}

	public void addRecipe(RecipeDTO dto) {
		rRepo.addRecipe(RecipeDTO.convertToRecipe(dto));
	}

	public void removeRecipe(RecipeDTO dto) {
		rRepo.removeRecipe(RecipeDTO.convertToRecipe(dto));
	}

	/**
	 * Sets the currently selected recipe.
	 * 
	 * @param dto The RecipeDTO for the Recipe to set.
	 */
	public void setCurrentRecipe(RecipeDTO dto) {
		setCurrentRecipe(dto.name());
	}

	/**
	 * Sets the currently selected recipe.
	 * 
	 * @param name The name property of the Recipe to set.
	 */
	public void setCurrentRecipe(String name) {
		try {
			Recipe r = rRepo.getRecipe(name);
			currentRecipe = r;
			notifySubscribers();
		} catch (IllegalArgumentException iae) {
			System.err.println("Could not set the current recipe. No recipe found for name.");
		}

	}

	public RecipeDTO getCurrentRecipe() {
		if (currentRecipe == null)
			return null;
		return RecipeDTO.convertToDTO(currentRecipe);
	}

	/**
	 * Removes oldRecipe and adds newRecipe.
	 * 
	 * @param oldRecipe
	 * @param newRecipe
	 */
	public void changeRecipe(RecipeDTO oldRecipe, RecipeDTO newRecipe) {
		rRepo.removeRecipe(RecipeDTO.convertToRecipe(oldRecipe));
		rRepo.addRecipe(RecipeDTO.convertToRecipe(newRecipe));
		setCurrentRecipe(newRecipe);
	}

	/*
	 * READ
	 */
	public RecipeDTO getRecipe(String name) {
		return RecipeDTO.convertToDTO(rRepo.getRecipe(name));
	}

	/**
	 * Returns a collection of RecipeDTOs for all recipes in the RecipeRepo
	 * 
	 * @return list of RecipeDTOs
	 */
	public Collection<RecipeDTO> getAllRecipes() {
		return rRepo.getRecipes().stream().map(r -> RecipeDTO.convertToDTO(r)).toList();
	}

	/*
	 * PUBLISHER INTERFACE
	 */

	@Override
	public void subscribe(Subscriber sub, ObserverEvent eventType) {
		currentRecipeSubscribers.add(sub);
	}

	@Override
	public void unsubscribe(Subscriber sub, ObserverEvent eventType) {
		currentRecipeSubscribers.remove(sub);
	}

	@Override
	public void notifySubscribers(ObserverEvent eventType) {
		currentRecipeSubscribers.forEach(sub -> sub.update());
	}

	/*
	 * REPO PUBLISHER INTERFACE
	 */
	public void subscribeToRepo(Subscriber sub) {
		rRepo.subscribe(sub);
	}

	public void unsubscribeFromRepo(Subscriber sub) {
		rRepo.unsubscribe(sub);
	}

}
