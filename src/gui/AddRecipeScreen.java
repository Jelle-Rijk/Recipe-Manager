package gui;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// TODO INPUT CONTROL
public class AddRecipeScreen extends VBox {
	private final RecipeController rc;
	private final MainScreen prevScreen;

	private String title;
	private String description;
	private int cookingTime;

	private Button btnSubmit;

	private final static double SPACING = 10;

	public AddRecipeScreen(RecipeController rc, MainScreen prevScreen) {
		this.prevScreen = prevScreen;
		this.rc = rc;
		buildGUI();
	}

	/*
	 * BUILD GUI
	 */
	private void buildGUI() {
		setAlignment(Pos.CENTER);
		setSpacing(SPACING);

		Label title = new Label("Add recipe");
		title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");

		buildInfoHeaderEditor();

		btnSubmit = new Button("Submit");
		btnSubmit.setOnAction(e -> submit());
		btnSubmit.setDisable(true);
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> returnToMainScreen());
		HBox buttons = new HBox(btnSubmit, cancel);
		getChildren().add(buttons);
	}

	private void buildInfoHeaderEditor() {
		FieldInput nameInput = new FieldInput("Recipe name", (title) -> setTitle(title));
		FieldInput descriptionInput = new FieldInput("Description", (desc) -> setDescription(desc));
		FieldInput cookingTimeInput = new FieldInput("Cooking time", (cookingTime) -> setCookingTime(cookingTime));

		getChildren().addAll(nameInput, descriptionInput, cookingTimeInput);

	}

	/*
	 * EVENT HANDLERS
	 */
	private void setTitle(String title) {
		try {
			if (title == null || title.isBlank())
				throw new IllegalArgumentException("Every recipe needs a name");
			if (!rc.isNameAvailable(title))
				throw new IllegalArgumentException("A recipe with that name already exists");
			this.title = title;
			updateSubmitButton();
		} catch (IllegalArgumentException iae) {
			this.title = null;
			updateSubmitButton();
			throw iae;
		}
	}

	private void setDescription(String description) {
		this.description = description;
	}

	private void setCookingTime(String cookingTime) {
		try {
			int time = cookingTime == null || cookingTime.isBlank() ? 0 : Integer.parseInt(cookingTime.trim());
			if (time < 0)
				throw new IllegalArgumentException("Cooking time cannot be negative");
			this.cookingTime = time;
		} catch (NumberFormatException e) {
			this.cookingTime = -1;
			updateSubmitButton();
			throw new IllegalArgumentException("Please enter an integer");
		} catch (IllegalArgumentException iae) {
			this.cookingTime = -1;
			updateSubmitButton();
			throw iae;
		}
		updateSubmitButton();
	}

	private void updateSubmitButton() {
		btnSubmit.setDisable(title == null || cookingTime == -1);
	}

	private void submit() {
		RecipeDTO dto = new RecipeDTO(title, description, cookingTime, new HashMap<String, Integer>(),
				new ArrayList<String>());
		rc.addRecipe(dto);
		rc.setCurrentRecipe(dto);
		returnToMainScreen();
	}

	private void returnToMainScreen() {
		getScene().getWindow().setWidth(prevScreen.getWidth());
		getScene().getWindow().setHeight(600);
		getScene().setRoot(prevScreen);
	}
}
