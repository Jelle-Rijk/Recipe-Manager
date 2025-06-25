package gui;

import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.scene.control.Button;

public class EditRecipeScreen extends AddRecipeScreen {
	RecipeDTO originalRecipe;
	Button btnDelete;

	public EditRecipeScreen(RecipeController rc) {
		super(rc);
		addDeleteButton();
		originalRecipe = rc.getCurrentRecipe();
		setInputFieldValues();
	}

	private void setInputFieldValues() {
		nameInput.setInput(originalRecipe.name());
		descriptionInput.setInput(originalRecipe.description());
		cookingTimeInput.setInput(Integer.toString(originalRecipe.cookingTime()));
	}

	private void addDeleteButton() {
		btnDelete = new Button("Delete");
		btnDelete.setOnAction(evt -> {
			rc.removeRecipe(originalRecipe);
			close();
		});
		buttons.getChildren().add(1, btnDelete);
	}

	@Override
	protected void submit() {
		super.submit();
		rc.removeRecipe(originalRecipe);
	}
}
