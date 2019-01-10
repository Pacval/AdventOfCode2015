package fr.rochet.days;

import fr.rochet.DayInterface;
import org.apache.commons.codec.digest.DigestUtils;

public class Day4 implements DayInterface {
    @Override
    public void part1() {
        String privateKey = "bgvyzdsv";

        int result = -1;
        int count = 0;

        while(result == -1) {
            count++;
            String publicKey = DigestUtils.md5Hex(privateKey + count);
            if(publicKey.length() >= 5 && publicKey.substring(0, 5).equals("00000")) {
                result = count;
            }
        }

        System.out.println("Lowest number to match : " + result);
    }

    @Override
    public void part2() {
        String privateKey = "bgvyzdsv";

        int result = -1;
        int count = 0;

        while(result == -1) {
            count++;
            String publicKey = DigestUtils.md5Hex(privateKey + count);
            if(publicKey.length() >= 5 && publicKey.substring(0, 6).equals("000000")) {
                result = count;
            }
        }

        System.out.println("Lowest number to match : " + result);

    }
}
