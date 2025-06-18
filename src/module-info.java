module RecipeManager {
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	requires javafx.graphics;
	requires javafx.controls;

	opens main to javafx.graphics;
}