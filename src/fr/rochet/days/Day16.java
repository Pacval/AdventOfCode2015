package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * --- Day 16: Aunt Sue ---
 * <p>
 * Your Aunt Sue has given you a wonderful gift, and you'd like to send her a thank you card. However, there's a small problem: she signed it "From, Aunt Sue".
 * <p>
 * You have 500 Aunts named "Sue".
 * <p>
 * So, to avoid sending the card to the wrong person, you need to figure out which Aunt Sue (which you conveniently number 1 to 500, for sanity) gave you the gift.
 * You open the present and, as luck would have it, good ol' Aunt Sue got you a My First Crime Scene Analysis Machine! Just what you wanted. Or needed, as the case may be.
 * <p>
 * The My First Crime Scene Analysis Machine (MFCSAM for short) can detect a few specific compounds in a given sample, as well as how many distinct kinds of those compounds there are.
 * According to the instructions, these are what the MFCSAM can detect:
 * <p>
 * children, by human DNA age analysis.
 * cats. It doesn't differentiate individual breeds.
 * Several seemingly random breeds of dog: samoyeds, pomeranians, akitas, and vizslas.
 * goldfish. No other kinds of fish.
 * trees, all in one group.
 * cars, presumably by exhaust or gasoline or something.
 * perfumes, which is handy, since many of your Aunts Sue wear a few kinds.
 * <p>
 * In fact, many of your Aunts Sue have many of these. You put the wrapping from the gift into the MFCSAM. It beeps inquisitively at you a few times and then prints out a message on ticker tape:
 * <p>
 * children: 3
 * cats: 7
 * samoyeds: 2
 * pomeranians: 3
 * akitas: 0
 * vizslas: 0
 * goldfish: 5
 * trees: 3
 * cars: 2
 * perfumes: 1
 * <p>
 * You make a list of the things you can remember about each Aunt Sue. Things missing from your list aren't zero - you simply don't remember the value.
 * <p>
 * What is the number of the Sue that got you the gift?
 * <p>
 * <p>
 * --- Part Two ---
 * <p>
 * As you're about to send the thank you note, something in the MFCSAM's instructions catches your eye. Apparently, it has an outdated retroencabulator,
 * and so the output from the machine isn't exact values - some of them indicate ranges.
 * <p>
 * In particular, the cats and trees readings indicates that there are greater than that many (due to the unpredictable nuclear decay of cat dander and tree pollen),
 * while the pomeranians and goldfish readings indicate that there are fewer than that many (due to the modial interaction of magnetoreluctance).
 * <p>
 * What is the number of the real Aunt Sue?
 */
public class Day16 implements DayInterface {

    private class Aunt {
        int number;
        int children;
        int cats;
        int samoyeds;
        int pomeranians;
        int akitas;
        int vizslas;
        int goldfish;
        int trees;
        int cars;
        int perfumes;

        Aunt(int number) {
            this.number = number;
            this.children = -1;
            this.cats = -1;
            this.samoyeds = -1;
            this.pomeranians = -1;
            this.akitas = -1;
            this.vizslas = -1;
            this.goldfish = -1;
            this.trees = -1;
            this.cars = -1;
            this.perfumes = -1;
        }

        int getNumber() {
            return number;
        }

        int getChildren() {
            return children;
        }

        int getCats() {
            return cats;
        }

        int getSamoyeds() {
            return samoyeds;
        }

        int getPomeranians() {
            return pomeranians;
        }

        int getAkitas() {
            return akitas;
        }

        int getVizslas() {
            return vizslas;
        }

        int getGoldfish() {
            return goldfish;
        }

        int getTrees() {
            return trees;
        }

        int getCars() {
            return cars;
        }

        int getPerfumes() {
            return perfumes;
        }

        void setChildren(int children) {
            this.children = children;
        }

        void setCats(int cats) {
            this.cats = cats;
        }

        void setSamoyeds(int samoyeds) {
            this.samoyeds = samoyeds;
        }

        void setPomeranians(int pomeranians) {
            this.pomeranians = pomeranians;
        }

        void setAkitas(int akitas) {
            this.akitas = akitas;
        }

        void setVizslas(int vizslas) {
            this.vizslas = vizslas;
        }

        void setGoldfish(int goldfish) {
            this.goldfish = goldfish;
        }

        void setTrees(int trees) {
            this.trees = trees;
        }

        void setCars(int cars) {
            this.cars = cars;
        }

        void setPerfumes(int perfumes) {
            this.perfumes = perfumes;
        }
    }

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(16);

        List<Aunt> aunts = new ArrayList<>();
        for (String entry : entries) {
            String[] caracteristics = entry.split(" ");
            Aunt aunt = new Aunt(Integer.parseInt(caracteristics[1].replaceAll(":", "")));

            for (int i = 2; i < caracteristics.length; i += 2) {
                switch (caracteristics[i].replaceAll(":", "")) {
                    case "children":
                        aunt.setChildren(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "cats":
                        aunt.setCats(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "samoyeds":
                        aunt.setSamoyeds(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "pomeranians":
                        aunt.setPomeranians(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "akitas":
                        aunt.setAkitas(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "vizslas":
                        aunt.setVizslas(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "goldfish":
                        aunt.setGoldfish(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "trees":
                        aunt.setTrees(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "cars":
                        aunt.setCars(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "perfumes":
                        aunt.setPerfumes(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                }
            }
            aunts.add(aunt);
        }

        // Caractéristiques recherchées
        int searchChildren = 3;
        int searchCats = 7;
        int searchSamoyeds = 2;
        int searchPomeranians = 3;
        int searchAkitas = 0;
        int searchVizslas = 0;
        int searchGoldfish = 5;
        int searchTrees = 3;
        int searchCars = 2;
        int searchPerfumes = 1;

        // et vive Java 8 et les streams
        List<Aunt> matchingAunts = aunts.stream()
                .filter(aunt -> aunt.getChildren() == -1 || aunt.getChildren() == searchChildren)
                .filter(aunt -> aunt.getCats() == -1 || aunt.getCats() == searchCats)
                .filter(aunt -> aunt.getSamoyeds() == -1 || aunt.getSamoyeds() == searchSamoyeds)
                .filter(aunt -> aunt.getPomeranians() == -1 || aunt.getPomeranians() == searchPomeranians)
                .filter(aunt -> aunt.getAkitas() == -1 || aunt.getAkitas() == searchAkitas)
                .filter(aunt -> aunt.getVizslas() == -1 || aunt.getVizslas() == searchVizslas)
                .filter(aunt -> aunt.getGoldfish() == -1 || aunt.getGoldfish() == searchGoldfish)
                .filter(aunt -> aunt.getTrees() == -1 || aunt.getTrees() == searchTrees)
                .filter(aunt -> aunt.getCars() == -1 || aunt.getCars() == searchCars)
                .filter(aunt -> aunt.getPerfumes() == -1 || aunt.getPerfumes() == searchPerfumes)
                .collect(Collectors.toList());

        if (matchingAunts.size() == 1) {
            System.out.println("Aunt who sent gift is : " + matchingAunts.get(0).getNumber());
        } else if (matchingAunts.isEmpty()) {
            System.out.println("No matching aunt");
        } else {
            System.out.println("Multiple matching aunts : " + matchingAunts.size());
        }
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(16);

        List<Aunt> aunts = new ArrayList<>();
        for (String entry : entries) {
            String[] caracteristics = entry.split(" ");
            Aunt aunt = new Aunt(Integer.parseInt(caracteristics[1].replaceAll(":", "")));

            for (int i = 2; i < caracteristics.length; i += 2) {
                switch (caracteristics[i].replaceAll(":", "")) {
                    case "children":
                        aunt.setChildren(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "cats":
                        aunt.setCats(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "samoyeds":
                        aunt.setSamoyeds(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "pomeranians":
                        aunt.setPomeranians(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "akitas":
                        aunt.setAkitas(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "vizslas":
                        aunt.setVizslas(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "goldfish":
                        aunt.setGoldfish(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "trees":
                        aunt.setTrees(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "cars":
                        aunt.setCars(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                    case "perfumes":
                        aunt.setPerfumes(Integer.parseInt(caracteristics[i + 1].replaceAll(",", "")));
                        break;
                }
            }
            aunts.add(aunt);
        }

        // Caractéristiques recherchées
        int searchChildren = 3;
        int searchCats = 7;
        int searchSamoyeds = 2;
        int searchPomeranians = 3;
        int searchAkitas = 0;
        int searchVizslas = 0;
        int searchGoldfish = 5;
        int searchTrees = 3;
        int searchCars = 2;
        int searchPerfumes = 1;

        // et vive Java 8 et les streams
        List<Aunt> matchingAunts = aunts.stream()
                .filter(aunt -> aunt.getChildren() == -1 || aunt.getChildren() == searchChildren)
                .filter(aunt -> aunt.getCats() == -1 || aunt.getCats() > searchCats)
                .filter(aunt -> aunt.getSamoyeds() == -1 || aunt.getSamoyeds() == searchSamoyeds)
                .filter(aunt -> aunt.getPomeranians() == -1 || aunt.getPomeranians() < searchPomeranians)
                .filter(aunt -> aunt.getAkitas() == -1 || aunt.getAkitas() == searchAkitas)
                .filter(aunt -> aunt.getVizslas() == -1 || aunt.getVizslas() == searchVizslas)
                .filter(aunt -> aunt.getGoldfish() == -1 || aunt.getGoldfish() < searchGoldfish)
                .filter(aunt -> aunt.getTrees() == -1 || aunt.getTrees() > searchTrees)
                .filter(aunt -> aunt.getCars() == -1 || aunt.getCars() == searchCars)
                .filter(aunt -> aunt.getPerfumes() == -1 || aunt.getPerfumes() == searchPerfumes)
                .collect(Collectors.toList());

        if (matchingAunts.size() == 1) {
            System.out.println("Real aunt who sent gift is : " + matchingAunts.get(0).getNumber());
        } else if (matchingAunts.isEmpty()) {
            System.out.println("No matching aunt");
        } else {
            System.out.println("Multiple matching aunts : " + matchingAunts.size());
        }
    }
}
