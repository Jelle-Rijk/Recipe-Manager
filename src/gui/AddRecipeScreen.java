package gui;

import java.util.ArrayList;
import java.util.HashMap;

import controllers.RecipeController;
import dto.RecipeDTO;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

// TODO INPUT CONTROL
public class AddRecipeScreen extends VBox {
	private final RecipeController rc;
	private final MainScreen prevScreen;

	private String title;
	private String description;
	private int cookingTime;

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

		buildTitleEditor();
		buildDescriptionEditor();
		buildCookingTimeEditor();

		Button submit = new Button("Submit");
		submit.setOnAction(e -> submit());
		Button cancel = new Button("Cancel");
		cancel.setOnAction(e -> returnToMainScreen());

		getChildren().addAll(submit, cancel);

	}

	private void buildTitleEditor() {
		HBox editor = buildTextfieldEditor("Recipe name");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnKeyTyped(e -> this.title = txf.getText());
		}
	}

	private void buildDescriptionEditor() {
		HBox editor = buildTextfieldEditor("Description");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnKeyTyped(e -> this.description = txf.getText());
		}
	}

	private void buildCookingTimeEditor() {
		HBox editor = buildTextfieldEditor("Cooking time");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnKeyTyped(e -> {
				this.cookingTime = Integer.parseInt(txf.getText());
			});
		}
	}

	/**
	 * Build an HBox with a label, region and a textfield
	 * 
	 * @param label
	 * @return HBox with a label, region and textfield
	 */
	private HBox buildTextfieldEditor(String label) {
		HBox editor = new HBox();
		editor.setMaxWidth(300);
		Label lblEditor = new Label(label);
		Region rgn = new Region();
		HBox.setHgrow(rgn, Priority.ALWAYS);

		TextField txfEditor = new TextField();
		txfEditor.setPromptText(String.format("Please enter %s", label.toLowerCase()));

		editor.getChildren().addAll(lblEditor, rgn, txfEditor);
		getChildren().add(editor);
		return editor;
	}

	/*
	 * EVENT HANDLERS
	 */
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
