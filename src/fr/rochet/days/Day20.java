package fr.rochet.days;

import fr.rochet.DayInterface;

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

    }
}
