package main;

import controllers.DomainController;
import gui.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Startup extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		DomainController dc = new DomainController();
		MainScreen root = new MainScreen(dc);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Recipe Manager");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
