package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
