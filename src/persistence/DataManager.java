package persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import cui.Logger;

public abstract class DataManager {
	private final static Path RECIPE_FILE_PATH = Path.of("data", "content", "recipes.ser");

	public static void createRecipeFile() {
		Path directory = Paths.get("data", "content");
		if (Files.notExists(directory))
			createDirectory(directory);

		try {
			Files.createFile(Path.of(directory.toString(), "recipes.ser"));
		} catch (IOException ioe) {
			Logger.logError("Could not create the recipe file");
		}
	}

	public static String getRecipeURI() {
		return RECIPE_FILE_PATH.toString();
	}

	private static void createDirectory(Path directory) {
		try {
			Files.createDirectories(directory);
			Logger.log(String.format("Created new directory: %s", directory.toString()));
		} catch (IOException ioe) {
			Logger.logError(String.format("New directories %s could not be created", directory.toString()));
		}
	}

}
