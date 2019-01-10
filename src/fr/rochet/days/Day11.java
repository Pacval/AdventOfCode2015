package fr.rochet.days;

import fr.rochet.DayInterface;
import org.apache.commons.lang3.ArrayUtils;

public class Day11 implements DayInterface {

    // Fonction privée pour tester la validité du password
    private boolean isPasswordCorrect(char[] password) {
        boolean increasing = false;
        for (int i = 0; i < password.length - 2; i++) {
            if (password[i] + 1 == password[i + 1]
                    && password[i + 1] + 1 == password[i + 2]) {
                increasing = true;
            }
        }

        boolean contain = !ArrayUtils.contains(password, 'i')
                && !ArrayUtils.contains(password, 'o')
                && !ArrayUtils.contains(password, 'l');

        int consecutive = 0;
        int i = 0;
        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                consecutive++;
                i++;
            }
            i++;
        }

        return increasing && contain && (consecutive >= 2);
    }

    // Fonction récursive permettant d'incrémenter le password
    private void increasePassword(char[] password, int pos) {
        if (password[pos] == 'z') {
            password[pos] = 'a';
            if (pos != 0) {
                increasePassword(password, pos - 1);
            }
        } else {
            password[pos] = (char) (password[pos] + 1);
            if(password[pos] == 'i' || password[pos] == 'o' || password[pos] == 'l') {
                password[pos] = (char) (password[pos] + 1);
            }
        }
    }

    @Override
    public void part1() {
        String entry = "hxbxwxba";

        char[] password = entry.toCharArray();
        do {
            increasePassword(password, password.length - 1);
        } while (!isPasswordCorrect(password));

        System.out.println("Next valid password : " + String.valueOf(password));
    }

    @Override
    public void part2() {
        String entry = "hxbxxyzz";

        char[] password = entry.toCharArray();
        do {
            increasePassword(password, password.length - 1);
        } while (!isPasswordCorrect(password));

        System.out.println("Next valid password : " + String.valueOf(password));
    }
}
