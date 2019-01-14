package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * --- Day 23: Opening the Turing Lock ---
 * <p>
 * Little Jane Marie just got her very first computer for Christmas from some unknown benefactor. It comes with instructions and an example program, but the computer itself seems to be malfunctioning.
 * She's curious what the program does, and would like you to help her run it.
 * <p>
 * The manual explains that the computer supports two registers and six instructions (truly, it goes on to remind the reader, a state-of-the-art technology).
 * The registers are named a and b, can hold any non-negative integer, and begin with a value of 0. The instructions are as follows:
 * <p>
 * hlf r sets register r to half its current value, then continues with the next instruction.
 * tpl r sets register r to triple its current value, then continues with the next instruction.
 * inc r increments register r, adding 1 to it, then continues with the next instruction.
 * jmp offset is a jump; it continues with the instruction offset away relative to itself.
 * jie r, offset is like jmp, but only jumps if register r is even ("jump if even").
 * jio r, offset is like jmp, but only jumps if register r is 1 ("jump if one", not odd).
 * <p>
 * All three jump instructions work with an offset relative to that instruction. The offset is always written with a prefix + or - to indicate the direction of the jump
 * (forward or backward, respectively). For example, jmp +1 would simply continue with the next instruction, while jmp +0 would continuously jump back to itself forever.
 * <p>
 * The program exits when it tries to run an instruction beyond the ones defined.
 * <p>
 * For example, this program sets a to 2, because the jio instruction causes it to skip the tpl instruction:
 * <p>
 * inc a
 * jio a, +2
 * tpl a
 * inc a
 * <p>
 * What is the value in register b when the program in your puzzle input is finished executing?
 * <p>
 * Your puzzle answer was 184.
 * --- Part Two ---
 * <p>
 * The unknown benefactor is very thankful for releasi-- er, helping little Jane Marie with her computer.
 * Definitely not to distract you, what is the value in register b after the program is finished executing if register a starts as 1 instead?
 */
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

            switch (inst.split(" ")[0]) {
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
                    if (registers.get(inst.split(" ")[1].substring(0, 1)) % 2 == 0) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
                case "jio":
                    if (registers.get(inst.split(" ")[1].substring(0, 1)) == 1) {
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

            switch (inst.split(" ")[0]) {
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
                    if (registers.get(inst.split(" ")[1].substring(0, 1)) % 2 == 0) {
                        position += Integer.parseInt(inst.split(" ")[2]);
                    } else {
                        position++;
                    }
                    break;
                case "jio":
                    if (registers.get(inst.split(" ")[1].substring(0, 1)) == 1) {
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
