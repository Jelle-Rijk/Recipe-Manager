package gui;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddRecipeScreen extends VBox {
	protected final RecipeController rc;
	private final Stage stage;

	protected String title;
	private String description;
	private int cookingTime;

	protected FieldInput nameInput;
	protected FieldInput descriptionInput;
	protected FieldInput cookingTimeInput;
	protected HBox buttons;
	private Button btnSubmit;

	private final static double SPACING = 10;

	public AddRecipeScreen(RecipeController rc) {
		this.rc = rc;
		this.stage = new Stage();
		buildGUI();
	}

	/*
	 * PUBLIC VOID
	 */
	public void show() {
		Scene scene = new Scene(this);

		stage.setScene(scene);
		stage.setTitle("Edit recipe");
		stage.show();
	};

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
		cancel.setOnAction(e -> close());
		buttons = new HBox(btnSubmit, cancel);
		getChildren().add(buttons);
	}

	protected void buildInfoHeaderEditor() {
		nameInput = new FieldInput("Recipe name", (title) -> setTitle(title));
		descriptionInput = new FieldInput("Description", (desc) -> setDescription(desc));
		cookingTimeInput = new FieldInput("Cooking time", (cookingTime) -> setCookingTime(cookingTime));

		getChildren().addAll(nameInput, descriptionInput, cookingTimeInput);

	}

	/*
	 * EVENT HANDLERS
	 */
	protected void setTitle(String title) {
		try {
			if (title == null || title.isBlank())
				throw new IllegalArgumentException("Every recipe needs a name");
			if (!rc.isNameAvailable(title)
					&& !(this instanceof EditRecipeScreen ers && title.equals(ers.originalRecipe.name()))) {
				throw new IllegalArgumentException("A recipe with that name already exists");
			}
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

	protected void updateSubmitButton() {
		btnSubmit.setDisable(title == null || cookingTime == -1);
	}

	protected void submit() {
		RecipeDTO dto = new RecipeDTO(title, description, cookingTime, new HashMap<String, Integer>(),
				new ArrayList<String>());
		rc.addRecipe(dto);
		rc.setCurrentRecipe(dto);
		close();
	}

	protected void close() {
		stage.close();
	}
}
