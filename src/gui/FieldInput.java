package gui;

import javafx.geometry.Insets;
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

	public void setInput(String input) {
		this.input.setText(input);
	}

	private void buildGUI() {
		setPadding(new Insets(0, 0, 0, 60));
		setSpacing(5);
		setPrefWidth(500);
		Label lblName = new Label(name);
		lblName.setPrefWidth(100);

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
