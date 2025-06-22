package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import utils.Setter;

public class FieldInput extends HBox implements Setter<String> {
	private final String name;
	private final Setter<String> setter;

	private TextField input;
	private Label errorLabel;

	public FieldInput(String name, Setter<String> setter) {
		this.name = name;
		this.setter = setter;
		buildGUI();
	}

	private void buildGUI() {
		Label lblName = new Label(name);

		input = new TextField();
		input.setOnKeyTyped((evt) -> set(input.getText()));
		input.setPromptText(String.format("Enter %s", name.toLowerCase()));

		errorLabel = new Label();

		getChildren().addAll(lblName, input, errorLabel);
	}

	@Override
	public void set(String str) {
		try {
			errorLabel.setText(null);
			setter.set(input.getText());
		} catch (Exception e) {
			errorLabel.setText(e.getMessage());
		}
	}
}
