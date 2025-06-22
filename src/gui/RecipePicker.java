package gui;

import controllers.RecipeController;
import dto.RecipeDTO;
import enums.ObserverEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import utils.Subscriber;

public class RecipePicker extends VBox implements Subscriber {
	private RecipeController rc;
	ListView<String> recipeList;

	public RecipePicker(RecipeController rc) {
		this.rc = rc;
		rc.subscribe(this);
		rc.subscribeToRepo(this);
		buildGUI();
	}

	private void buildGUI() {
		Label pick = new Label("Pick a recipe");
		recipeList = new ListView<String>();
		recipeList.setOnMouseClicked(e -> rc.setCurrentRecipe(recipeList.getSelectionModel().getSelectedItem()));

		getChildren().addAll(pick, recipeList);
		update(ObserverEvent.LIST_CHANGE);
	}

	/*
	 * UPDATE
	 */
	@Override
	public void update(ObserverEvent eventType) {
		if (eventType == ObserverEvent.LIST_CHANGE) {
			recipeList.getItems().clear();
			rc.getAllRecipes().forEach(dto -> recipeList.getItems().add(dto.name()));
		}
		RecipeDTO currentRecipe = rc.getCurrentRecipe();
		if (currentRecipe != null)
			recipeList.getSelectionModel().select(currentRecipe.name());
	}

}
