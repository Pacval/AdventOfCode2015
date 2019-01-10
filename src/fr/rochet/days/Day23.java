package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.HashMap;

public class Day23 implements DayInterface {

    @Override
    public void part1() throws IOException {
        String[] instructions = ExoUtils.getEntries(23);

        int position = 0;

        // on va stocker les variables dans une Map comme ça on pourra les retrouver par leur nom (pas en testant if (nom == a))
        HashMap<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);

        while (position >= 0 && position < instructions.length) {
            String inst = instructions[position];

            switch (inst.split(" ")[0]){
                case "hlf":
                    String registerHlf = inst.split(" ")[1];
                    registers.replace(registerHlf, registers.get(registerHlf) / 2);
                    position++;
                    break;
                case "tpl":
                    String registerTpl = inst.split(" ")[1];
                    registers.replace(registerTpl, registers.get(registerTpl) * 3);
                    position++;
                    break;
                case "inc":
                    String registerInc = inst.split(" ")[1];
                    registers.replace(registerInc, registers.get(registerInc) + 1);
                    position++;
                    break;
                case "jmp":
                    position += Integer.parseInt(inst.split(" ")[1]);
                    break;
                case "jie":
                    if (registers.get(inst.split(" ")[1].substring(0,1)) % 2 == 0) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
                case "jio":
                    if (registers.get(inst.split(" ")[1].substring(0,1)) == 1) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
            }
        }

        System.out.println("Finals values of register are : ");
        System.out.println(registers.entrySet().toString());
    }

    @Override
    public void part2() throws IOException {
        String[] instructions = ExoUtils.getEntries(23);

        int position = 0;

        // on va stocker les variables dans une Map comme ça on pourra les retrouver par leur nom (pas en testant if (nom == a))
        HashMap<String, Integer> registers = new HashMap<>();
        registers.put("a", 1);
        registers.put("b", 0);

        while (position >= 0 && position < instructions.length) {
            String inst = instructions[position];

            switch (inst.split(" ")[0]){
                case "hlf":
                    String registerHlf = inst.split(" ")[1];
                    registers.replace(registerHlf, registers.get(registerHlf) / 2);
                    position++;
                    break;
                case "tpl":
                    String registerTpl = inst.split(" ")[1];
                    registers.replace(registerTpl, registers.get(registerTpl) * 3);
                    position++;
                    break;
                case "inc":
                    String registerInc = inst.split(" ")[1];
                    registers.replace(registerInc, registers.get(registerInc) + 1);
                    position++;
                    break;
                case "jmp":
                    position += Integer.parseInt(inst.split(" ")[1]);
                    break;
                case "jie":
                    if (registers.get(inst.split(" ")[1].substring(0,1)) % 2 == 0) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
                case "jio":
                    if (registers.get(inst.split(" ")[1].substring(0,1)) == 1) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
            }
        }

        System.out.println("Finals values of register are (a starts at 1) : ");
        System.out.println(registers.entrySet().toString());
    }
}
