package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;
import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pour que ce soit plus drôle on va partir du principe qu'on ne connait pas le nombre d'ingrédients dont on dispose au début.
 */
public class Day15 implements DayInterface {

    private class Ingredient {
        private String name;
        private int capacity;
        private int durability;
        private int flavor;
        private int texture;
        private int calories;

        public Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
            this.name = name;
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public String getName() {
            return name;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getDurability() {
            return durability;
        }

        public int getFlavor() {
            return flavor;
        }

        public int getTexture() {
            return texture;
        }

        public int getCalories() {
            return calories;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private List<HashMap<Ingredient, Integer>> recipes;

    private void makeRecipe(List<Ingredient> ingredients, int quantityLeft, HashMap<Ingredient, Integer> recipe) {
        // On fait une copie pour pas toucher à la liste d'origine
        List<Ingredient> ingredientCopy = new ArrayList<>(ingredients);
        Ingredient workingIngredient = ingredientCopy.remove(0);

        if (ingredientCopy.isEmpty()) {
            // Si il ne reste plus d'ingrédient, on rempli la place restante dans la recette
            recipe.put(workingIngredient, quantityLeft);
            recipes.add(new HashMap<>(recipe));

        } else {
            recipe.put(workingIngredient, 0);
            for (int i = 0; i <= quantityLeft; i++) {
                recipe.replace(workingIngredient, i);
                makeRecipe(ingredientCopy, quantityLeft - i, recipe);
            }
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(15);

        int quantity = 100;

        List<Ingredient> ingredients = new ArrayList<>();
        for (String entry : entries) {
            ingredients.add(new Ingredient(
                    entry.split(" ")[0].replaceAll(":", ""),
                    Integer.parseInt(entry.split(" ")[2].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[4].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[6].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[8].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[10])
            ));
        }

        recipes = new ArrayList<>();
        makeRecipe(ingredients, quantity, new HashMap<>());

        int highScore = 0;
        HashMap<Ingredient, Integer> highScoreRecipe = new HashMap<>();
        for (HashMap<Ingredient, Integer> recipe : recipes) {
            int capacityScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getCapacity() * item.getValue()).sum();
            int durabilityScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getDurability() * item.getValue()).sum();
            int flavorScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getFlavor() * item.getValue()).sum();
            int textureScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getTexture() * item.getValue()).sum();

            if (capacityScore > 0 && durabilityScore > 0 && flavorScore > 0 && textureScore > 0) {
                int totalScore = capacityScore * durabilityScore * flavorScore * textureScore;
                if(totalScore > highScore) {
                    highScore = totalScore;
                    highScoreRecipe = recipe;
                }
            }
        }

        System.out.println("Highscore is : " + highScore);
        System.out.println("For recipe : " + highScoreRecipe);

    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(15);

        int quantity = 100;
        int maxCalories = 500;

        List<Ingredient> ingredients = new ArrayList<>();
        for (String entry : entries) {
            ingredients.add(new Ingredient(
                    entry.split(" ")[0].replaceAll(":", ""),
                    Integer.parseInt(entry.split(" ")[2].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[4].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[6].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[8].replaceAll(",", "")),
                    Integer.parseInt(entry.split(" ")[10])
            ));
        }

        recipes = new ArrayList<>();
        makeRecipe(ingredients, quantity, new HashMap<>());

        int highScore = 0;
        HashMap<Ingredient, Integer> highScoreRecipe = new HashMap<>();
        for (HashMap<Ingredient, Integer> recipe : recipes) {
            int capacityScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getCapacity() * item.getValue()).sum();
            int durabilityScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getDurability() * item.getValue()).sum();
            int flavorScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getFlavor() * item.getValue()).sum();
            int textureScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getTexture() * item.getValue()).sum();
            int caloriesScore = recipe.entrySet().stream().mapToInt(item -> item.getKey().getCalories() * item.getValue()).sum();

            if (capacityScore > 0 && durabilityScore > 0 && flavorScore > 0 && textureScore > 0 && caloriesScore <= maxCalories) {
                int totalScore = capacityScore * durabilityScore * flavorScore * textureScore;
                if(totalScore > highScore) {
                    highScore = totalScore;
                    highScoreRecipe = recipe;
                }
            }
        }

        System.out.println("Highscore with calories is : " + highScore);
        System.out.println("For recipe : " + highScoreRecipe);

    }
}
