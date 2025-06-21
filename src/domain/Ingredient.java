package domain;

public class Ingredient {
	private final String name;

	public Ingredient(String name) {
		if (name == null || name.isBlank())
			throw new IllegalArgumentException();
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
