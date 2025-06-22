package persistence.recipes;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cui.Logger;
import domain.recipes.Recipe;
import persistence.DataManager;

public class RecipeServiceImpl implements RecipeService {
	public static String FILE_PATH;

	public RecipeServiceImpl() {
		FILE_PATH = DataManager.getRecipeURI();
	}

	@Override
	public List<Recipe> loadRecipes() {
		List<Recipe> data = new ArrayList<Recipe>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
			while (true)
				data.add((Recipe) ois.readObject());
		} catch (FileNotFoundException fnfe) {
			DataManager.createRecipeFile();
			return loadRecipes();
		} catch (EOFException eof) {
			Logger.log(String.format("Loaded %d recipes.", data.size()), this);
		} catch (Exception e) {
			Logger.logError(String.format("%s thrown in loadRecipes()", e.getClass().getSimpleName()), this);
		}
		return data;
	}

	private void saveRecipes(List<Recipe> recipes) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
			for (Recipe r : recipes)
				oos.writeObject(r);
			System.out.printf("Saved %d recipes", recipes.size());
		} catch (FileNotFoundException fnfe) {
			DataManager.createRecipeFile();
			saveRecipes(recipes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveRecipe(Recipe r) {
		List<Recipe> recipes = loadRecipes();
		recipes.add(r);
		saveRecipes(recipes);

	}

	@Override
	public void deleteRecipe(Recipe r) {
		List<Recipe> recipes = loadRecipes();
		recipes.remove(r);
		saveRecipes(recipes);
	}

}
