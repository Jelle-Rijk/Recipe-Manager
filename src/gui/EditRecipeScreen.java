package gui;

import controllers.DomainController;
import dto.RecipeDTO;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EditRecipeScreen extends VBox {
	private DomainController dc;
	private final RecipeDTO oldRecipe;
	private String title;
	private String description;
	private int cookingTime;

	public EditRecipeScreen(RecipeDTO recipe, DomainController dc) {
		this.dc = dc;
		this.oldRecipe = recipe;
		this.title = recipe.name();
		this.description = recipe.description();
		this.cookingTime = recipe.cookingTime();
		buildGUI();
	}

	private void buildGUI() {
		buildTitleEditor();
		buildDescriptionEditor();
		buildCookingTimeEditor();

		Button submit = new Button("Submit");
		submit.setOnAction(e -> submit());

		getChildren().add(submit);

	}

	private void buildTitleEditor() {
		HBox editor = buildTextfieldEditor("Recipe name");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnAction(e -> {
				this.title = txf.getText();
			});
		}
	}

	private void buildDescriptionEditor() {
		HBox editor = buildTextfieldEditor("Description");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnAction(e -> {
				this.description = txf.getText();
			});
		}
	}

	private void buildCookingTimeEditor() {
		HBox editor = buildTextfieldEditor("Cooking time");
		if (editor.getChildren().getLast() instanceof TextField txf) {
			txf.setOnAction(e -> {
				this.cookingTime = Integer.parseInt(txf.getText());
			});
		}
	}

	/**
	 * Build an HBox with a label and a textfield
	 * 
	 * @param label
	 * @return HBox with a label and textfield
	 */
	private HBox buildTextfieldEditor(String label) {
		HBox editor = new HBox();
		Label lblEditor = new Label(label);

		TextField txfEditor = new TextField();
		txfEditor.setPromptText(String.format("Please enter %s", label.toLowerCase()));

		editor.getChildren().addAll(lblEditor, txfEditor);
		getChildren().add(editor);
		return editor;
	}

	private void submit() {
		RecipeDTO dto = new RecipeDTO(title, description, cookingTime, oldRecipe.ingredientAmounts(),
				oldRecipe.instructions());
		dc.getRecipeController().addRecipe(dto);
		RecipeDTO newRecipe = dc.getRecipeController().getRecipe(dto.name());

		RecipeView rv = new RecipeView(newRecipe);
		getScene().getWindow().setWidth(rv.getWidth());
		getScene().getWindow().setHeight(rv.getHeight());
		getScene().setRoot(rv);

	}
}
