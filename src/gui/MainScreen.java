package gui;

import controllers.DomainController;
import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {
	private final DomainController dc;

	public MainScreen(DomainController dc) {
		if (dc == null)
			throw new IllegalStateException("DomainController was not set");
		this.dc = dc;
		buildTestGUI();
	}

	private void buildTestGUI() {
		RecipeController rc = dc.getRecipeController();
		rc.addRecipe(null);
		RecipeDTO recipe = dc.getRecipe("Pasta Carbonara");
		setCenter(new RecipeScreen(recipe));
	}
}
