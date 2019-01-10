package fr.rochet.days;

import fr.rochet.DayInterface;

import java.util.HashMap;

public class Day20 implements DayInterface {

    @Override
    public void part1() {
        int nbGifts = 33100000;

        int house = 0;
        int giftForHouse = 0;

        while (giftForHouse <= nbGifts) {
            house++;
            giftForHouse = 0;
            for (int elv = 1; elv <= house; elv++) {
                if (house % elv == 0) {
                    giftForHouse += elv * 10;
                }
            }
            System.out.println(house + "\t\t" + giftForHouse);
        }

        System.out.println("First house to have at least " + nbGifts + " gifts : " + house);
    }

    @Override
    public void part2() {
        int nbGifts = 33100000;

        int house = 0;
        int giftForHouse = 0;
        HashMap<Integer, Integer> elvesWork = new HashMap<>();

        while (giftForHouse <= nbGifts) {
            house++;
            giftForHouse = 0;

            for (int elv = 1; elv <= house; elv++) {
                // On ajoute l'elf à la liste
                elvesWork.put(house, 50);
                if (house % elv == 0 && elvesWork.containsKey(elv)) {
                    giftForHouse += elv * 11;
                    elvesWork.put(elv, elvesWork.get(elv) - 1);
                    // Si l'elf n'a plus de cadeaux à distribuer, on le vire de la liste pour gagner de la place
                    if (elvesWork.get(elv) == 0) {
                        elvesWork.remove(elv);
                    }
                }
            }
        }

        System.out.println("First house to have at least " + nbGifts + " gifts (with lazy elves) : " + house);
    }
}
