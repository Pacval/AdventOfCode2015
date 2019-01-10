package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
