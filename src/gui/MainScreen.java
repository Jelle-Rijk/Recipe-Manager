package gui;

import controllers.DomainController;
import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		RecipeDTO recipe = rc.getRecipe("Pasta Carbonara");

		setCenter(new RecipeView(recipe));
		Button editRecipe = new Button("Edit recipe");
		editRecipe.setOnAction(e -> editRecipe(recipe));
		setBottom(editRecipe);
	}

	private void editRecipe(RecipeDTO recipe) {
		EditRecipeScreen screen = new EditRecipeScreen(recipe, dc);
		Scene scene = getScene();
		scene.setRoot(screen);
		scene.getWindow().setWidth(screen.getWidth());
		scene.getWindow().setHeight(screen.getHeight());

	}
}
