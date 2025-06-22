package gui;

import controllers.DomainController;
import controllers.RecipeController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MainScreen extends BorderPane {
	private final DomainController dc;

	public MainScreen(DomainController dc) {
		if (dc == null)
			throw new IllegalStateException("DomainController was not set");
		this.dc = dc;
		buildGUI();
	}

	private void buildGUI() {
		setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		setPrefSize(USE_COMPUTED_SIZE, USE_COMPUTED_SIZE);
		setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);

		RecipeController rc = dc.getRecipeController();
		setCenter(new RecipeView(rc));
		Button editRecipe = new Button("Edit recipe");
		editRecipe.setOnAction(e -> editRecipe(rc));
		setBottom(editRecipe);
		setLeft(new RecipePicker(rc));
	}

	private void editRecipe(RecipeController rc) {
		AddRecipeScreen screen = new AddRecipeScreen(rc, this);
		Scene scene = getScene();
		scene.setRoot(screen);
		scene.getWindow().setWidth(screen.getWidth());
		scene.getWindow().setHeight(screen.getHeight());

	}
}
