package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 21: RPG Simulator 20XX ---
 * <p>
 * Little Henry Case got a new video game for Christmas. It's an RPG, and he's stuck on a boss. He needs to know what equipment to buy at the shop. He hands you the controller.
 * <p>
 * In this game, the player (you) and the enemy (the boss) take turns attacking. The player always goes first. Each attack reduces the opponent's hit points by at least 1.
 * The first character at or below 0 hit points loses.
 * <p>
 * Damage dealt by an attacker each turn is equal to the attacker's damage score minus the defender's armor score. An attacker always does at least 1 damage.
 * So, if the attacker has a damage score of 8, and the defender has an armor score of 3, the defender loses 5 hit points. If the defender had an armor score of 300, the defender would still lose 1 hit point.
 * <p>
 * Your damage score and armor score both start at zero. They can be increased by buying items in exchange for gold. You start with no items and have as much gold as you need.
 * Your total damage or armor is equal to the sum of those stats from all of your items. You have 100 hit points.
 * <p>
 * Here is what the item shop is selling:
 * <p>
 * Weapons:    Cost  Damage  Armor
 * Dagger        8     4       0
 * Shortsword   10     5       0
 * Warhammer    25     6       0
 * Longsword    40     7       0
 * Greataxe     74     8       0
 * <p>
 * Armor:      Cost  Damage  Armor
 * Leather      13     0       1
 * Chainmail    31     0       2
 * Splintmail   53     0       3
 * Bandedmail   75     0       4
 * Platemail   102     0       5
 * <p>
 * Rings:      Cost  Damage  Armor
 * Damage +1    25     1       0
 * Damage +2    50     2       0
 * Damage +3   100     3       0
 * Defense +1   20     0       1
 * Defense +2   40     0       2
 * Defense +3   80     0       3
 * <p>
 * You must buy exactly one weapon; no dual-wielding. Armor is optional, but you can't use more than one. You can buy 0-2 rings (at most one for each hand).
 * You must use any items you buy. The shop only has one of each item, so you can't buy, for example, two rings of Damage +3.
 * <p>
 * For example, suppose you have 8 hit points, 5 damage, and 5 armor, and that the boss has 12 hit points, 7 damage, and 2 armor:
 * <p>
 * The player deals 5-2 = 3 damage; the boss goes down to 9 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 6 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 6 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 4 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 3 hit points.
 * The boss deals 7-5 = 2 damage; the player goes down to 2 hit points.
 * The player deals 5-2 = 3 damage; the boss goes down to 0 hit points.
 * <p>
 * In this scenario, the player wins! (Barely.)
 * <p>
 * You have 100 hit points. The boss's actual stats are in your puzzle input. What is the least amount of gold you can spend and still win the fight?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * Turns out the shopkeeper is working with the boss, and can persuade you to buy whatever items he wants. The other rules still apply, and he still only has one of each item.
 * <p>
 * What is the most amount of gold you can spend and still lose the fight?
 */
public class Day21 implements DayInterface {

    private enum Type {
        WEAPON, ARMOR, RING
    }

    private class Item {
        private String name;
        private Type type;
        private int damages;
        private int armor;
        private int cost;

        Item(String name, Type type, int damages, int armor, int cost) {
            this.name = name;
            this.type = type;
            this.damages = damages;
            this.armor = armor;
            this.cost = cost;
        }

        String getName() {
            return name;
        }

        Type getType() {
            return type;
        }

        int getDamages() {
            return damages;
        }

        int getArmor() {
            return armor;
        }

        int getCost() {
            return cost;
        }
    }

    private class Perso {
        private int pv;
        private int damages;
        private int armor;

        Perso(int pv, int damages, int armor) {
            this.pv = pv;
            this.damages = damages;
            this.armor = armor;
        }

        int getPv() {
            return pv;
        }

        int getDamages() {
            return damages;
        }

        int getArmor() {
            return armor;
        }
    }

    private List<Item> initShop() {
        List<Item> shop = new ArrayList<>();

        shop.add(new Item("Dagger", Type.WEAPON, 4, 0, 8));
        shop.add(new Item("Shortsword", Type.WEAPON, 5, 0, 10));
        shop.add(new Item("Warhammer", Type.WEAPON, 6, 0, 25));
        shop.add(new Item("Longsword", Type.WEAPON, 7, 0, 40));
        shop.add(new Item("Greataxe", Type.WEAPON, 8, 0, 74));

        shop.add(new Item("Leather", Type.ARMOR, 0, 1, 13));
        shop.add(new Item("Chainmail", Type.ARMOR, 0, 2, 31));
        shop.add(new Item("Splintmail", Type.ARMOR, 0, 3, 53));
        shop.add(new Item("Bandedmail", Type.ARMOR, 0, 4, 75));
        shop.add(new Item("Platemail", Type.ARMOR, 0, 5, 102));

        shop.add(new Item("Damage +1", Type.RING, 1, 0, 25));
        shop.add(new Item("Damage +2", Type.RING, 2, 0, 50));
        shop.add(new Item("Damage +3", Type.RING, 3, 0, 100));
        shop.add(new Item("Defense +1", Type.RING, 0, 1, 20));
        shop.add(new Item("Defense +2", Type.RING, 0, 2, 40));
        shop.add(new Item("Defense +3", Type.RING, 0, 3, 80));

        return shop;
    }

    private List<List<Item>> getAllStuff(List<Item> shop) {
        List<Item> weapons = shop.stream().filter(item -> item.getType() == Type.WEAPON).collect(Collectors.toList());

        List<Item> armors = shop.stream().filter(item -> item.getType() == Type.ARMOR).collect(Collectors.toList());
        Item noArmor = new Item("", Type.ARMOR, 0, 0, 0);
        armors.add(noArmor);

        List<Item> rings = shop.stream().filter(item -> item.getType() == Type.RING).collect(Collectors.toList());
        Item noRing = new Item("", Type.RING, 0, 0, 0);
        rings.add(noRing);

        List<List<Item>> allStuffs = new ArrayList<>();
        // On teste toutes les combinaisons possibles de stuff
        for (Item weapon : weapons) {
            for (Item armor : armors) {
                for (Item ring1 : rings) {
                    for (Item ring2 : rings) {
                        // seul point à vérifier pour pas avoir de mauvais stuff : que les 2 bagues ne soient pas les memes
                        // exception si les 2 sont "pas de bague"
                        if (!ring1.getName().equals(ring2.getName()) || ring1.getName().equals("")) {
                            List<Item> stuff = new ArrayList<>();
                            stuff.add(weapon);
                            if (!armor.getName().equals("")) {
                                stuff.add(armor);
                            }
                            if (!ring1.getName().equals("")) {
                                stuff.add(ring1);
                            }
                            if (!ring2.getName().equals("")) {
                                stuff.add(ring2);
                            }
                            allStuffs.add(stuff);
                        }
                    }
                }
            }
        }

        return allStuffs;
    }

    private boolean youWin(Perso you, List<Item> stuff, Perso boss) {
        // EXPLICATION
        int yourDamages = you.getDamages() + stuff.stream().mapToInt(Item::getDamages).sum();
        int yourArmor = you.getArmor() + stuff.stream().mapToInt(Item::getArmor).sum();

        //      Dégats infligés au boss chaque tour
        return (you.getPv() / (boss.getDamages() - yourArmor > 0 ? boss.getDamages() - yourArmor : 1) + (you.getPv() % (boss.getDamages() - yourArmor > 0 ? boss.getDamages() - yourArmor : 1) == 0 ? 0 : 1))
                // Multiplié par le nombre de tours avant d'être tué
                * (yourDamages - boss.getArmor() > 0 ? yourDamages - boss.getArmor() : 1)
                // Si supérieur à la vie du boss -> victoire
                >= boss.getPv();
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(21);

        Perso boss = new Perso(
                Integer.parseInt(entries[0].split(" ")[2]),
                Integer.parseInt(entries[1].split(" ")[1]),
                Integer.parseInt(entries[2].split(" ")[1])
        );
        Perso you = new Perso(100, 0, 0);

        List<Item> shop = initShop();

        List<List<Item>> allStuff = getAllStuff(shop);
        List<List<Item>> allStuffOrderedByCost = allStuff.stream().sorted(Comparator.comparing(items -> items.stream().mapToInt(Item::getCost).sum())).collect(Collectors.toList());

        int i = 0;

        while (!youWin(you, allStuffOrderedByCost.get(i), boss)) {
            i++;
        }

        List<Item> winningStuff = allStuffOrderedByCost.get(i);
        System.out.println("Your winning stuff is : " + winningStuff.stream().map(Item::getName).collect(Collectors.joining(" / ")));
        System.out.println("It costs : " + winningStuff.stream().mapToInt(Item::getCost).sum());
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(21);

        Perso boss = new Perso(
                Integer.parseInt(entries[0].split(" ")[2]),
                Integer.parseInt(entries[1].split(" ")[1]),
                Integer.parseInt(entries[2].split(" ")[1])
        );
        Perso you = new Perso(100, 0, 0);

        List<Item> shop = initShop();

        List<List<Item>> allStuff = getAllStuff(shop);
        List<List<Item>> allStuffOrderedByCost = allStuff.stream().sorted(Comparator.comparing(items -> items.stream().mapToInt(Item::getCost).sum())).collect(Collectors.toList());

        int i = allStuffOrderedByCost.size() - 1;

        while (youWin(you, allStuffOrderedByCost.get(i), boss)) {
            i--;
        }

        List<Item> winningStuff = allStuffOrderedByCost.get(i);
        System.out.println("Your most loosing stuff is : " + winningStuff.stream().map(Item::getName).collect(Collectors.joining(" / ")));
        System.out.println("It costs : " + winningStuff.stream().mapToInt(Item::getCost).sum());
    }
}
