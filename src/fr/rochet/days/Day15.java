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
 * --- Day 15: Science for Hungry People ---
 * <p>
 * Today, you set out on the task of perfecting your milk-dunking cookie recipe. All you have to do is find the right balance of ingredients.
 * <p>
 * Your recipe leaves room for exactly 100 teaspoons of ingredients. You make a list of the remaining ingredients you could use to finish the recipe (your puzzle input) and their properties per teaspoon:
 * <p>
 * capacity (how well it helps the cookie absorb milk)
 * durability (how well it keeps the cookie intact when full of milk)
 * flavor (how tasty it makes the cookie)
 * texture (how it improves the feel of the cookie)
 * calories (how many calories it adds to the cookie)
 * <p>
 * You can only measure ingredients in whole-teaspoon amounts accurately, and you have to be accurate so you can reproduce your results in the future.
 * The total score of a cookie can be found by adding up each of the properties (negative totals become 0) and then multiplying together everything except calories.
 * <p>
 * For instance, suppose you have these two ingredients:
 * <p>
 * Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
 * Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3
 * <p>
 * Then, choosing to use 44 teaspoons of butterscotch and 56 teaspoons of cinnamon (because the amounts of each ingredient must add up to 100) would result in a cookie with the following properties:
 * <p>
 * A capacity of 44*-1 + 56*2 = 68
 * A durability of 44*-2 + 56*3 = 80
 * A flavor of 44*6 + 56*-2 = 152
 * A texture of 44*3 + 56*-1 = 76
 * <p>
 * Multiplying these together (68 * 80 * 152 * 76, ignoring calories for now) results in a total score of 62842880, which happens to be the best score possible given these ingredients.
 * If any properties had produced a negative total, it would have instead become zero, causing the whole score to multiply to zero.
 * <p>
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Your cookie recipe becomes wildly popular! Someone asks if you can make another recipe that has exactly 500 calories per cookie (so they can use it as a meal replacement).
 * Keep the rest of your award-winning process the same (100 teaspoons, same ingredients, same scoring system).
 * <p>
 * For example, given the ingredients above, if you had instead selected 40 teaspoons of butterscotch and 60 teaspoons of cinnamon (which still adds to 100), the total calorie count would be 40*8 + 60*3 = 500.
 * The total score would go down, though: only 57600000, the best you can do in such trying circumstances.
 * <p>
 * Given the ingredients in your kitchen and their properties, what is the total score of the highest-scoring cookie you can make with a calorie total of 500?
 * <p>
 * <p>
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

        Ingredient(String name, int capacity, int durability, int flavor, int texture, int calories) {
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

        int getCapacity() {
            return capacity;
        }

        int getDurability() {
            return durability;
        }

        int getFlavor() {
            return flavor;
        }

        int getTexture() {
            return texture;
        }

        int getCalories() {
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
                if (totalScore > highScore) {
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
                if (totalScore > highScore) {
                    highScore = totalScore;
                    highScoreRecipe = recipe;
                }
            }
        }

        System.out.println("Highscore with calories is : " + highScore);
        System.out.println("For recipe : " + highScoreRecipe);

    }
}
