package controllers;

public class DomainController {
	private final RecipeController rc;

	public DomainController() {
		rc = new RecipeController();
	}

	public RecipeController getRecipeController() {
		return rc;
	}
}
