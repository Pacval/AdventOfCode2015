package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day7 implements DayInterface {

    private class Instruction {
        String toDo;
        String result;
        Short value;

        public Instruction(String toDo, String result) {
            this.toDo = toDo;
            this.result = result;
            this.value = null;
        }

        public String getResult() {
            return result;
        }

        public void setToDo(String toDo) {
            this.toDo = toDo;
        }

        // Fonction récursive qui va nous permettre de partir de la variable souhaitée pour remonter jusqu'au variables d'origine
        public Short getValue() {
            // System.out.println("Instruction : " + this.toDo + " -> " + this.result);
            if (this.value == null) {
                // Analyse instruction
                if (this.toDo.split(" ").length == 1) {
                    try {
                        // 44430 -> b
                        // Tricky : les nombres sont en unsigned. Du coup comme on stocke en short ça peut dépasser et faire péter si on transforme direct en short
                        // Du coup on passe par un Integer, et avec la gestion java si on est supérieur à la limite mémoire short, alors on passe dans les négatifs lors du cast
                        // Coté bits, ça change rien, mais de notre coté les valeurs peuvent etre négatives
                        this.value = (short) Integer.parseInt(toDo);
                    } catch (NumberFormatException e) {
                        // lx -> a
                        this.value = instructions.stream()
                                .filter(inst -> inst.getResult().equals(toDo))
                                .findFirst()
                                .map(Instruction::getValue)
                                .get();
                    }
                } else if (this.toDo.split(" ")[0].equals("NOT")) {
                    // NOT dq
                    String var = this.toDo.split(" ")[1];

                    short valueVar = instructions.stream()
                            .filter(inst -> inst.getResult().equals(var))
                            .findFirst()
                            .map(Instruction::getValue)
                            .get();

                    this.value = (short) ~valueVar;

                } else if (this.toDo.split(" ")[1].equals("AND")) {
                    // eg AND ei -> ej
                    String var1 = this.toDo.split(" ")[0];
                    String var2 = this.toDo.split(" ")[2];

                    short valueVar1;
                    try {
                        valueVar1 = Short.parseShort(var1);
                    } catch (NumberFormatException e) {
                        valueVar1 = instructions.stream()
                                .filter(inst -> inst.getResult().equals(var1))
                                .findFirst()
                                .map(Instruction::getValue)
                                .get();
                    }

                    short valueVar2;
                    try {
                        valueVar2 = Short.parseShort(var2);
                    } catch (NumberFormatException e) {
                        valueVar2 = instructions.stream()
                                .filter(inst -> inst.getResult().equals(var2))
                                .findFirst()
                                .map(Instruction::getValue)
                                .get();
                    }

                    this.value = (short) (valueVar1 & valueVar2);

                } else if (this.toDo.split(" ")[1].equals("OR")) {
                    // ep OR eo -> eq
                    String var1 = this.toDo.split(" ")[0];
                    String var2 = this.toDo.split(" ")[2];

                    short valueVar1 = instructions.stream()
                            .filter(inst -> inst.getResult().equals(var1))
                            .findFirst()
                            .map(Instruction::getValue)
                            .get();

                    short valueVar2 = instructions.stream()
                            .filter(inst -> inst.getResult().equals(var2))
                            .findFirst()
                            .map(Instruction::getValue)
                            .get();

                    this.value = (short) (valueVar1 | valueVar2);

                } else if (this.toDo.split(" ")[1].equals("LSHIFT")) {
                    // kf LSHIFT 15 -> kj
                    String var1 = this.toDo.split(" ")[0];
                    String var2 = this.toDo.split(" ")[2];

                    short valueVar1 = instructions.stream()
                            .filter(inst -> inst.getResult().equals(var1))
                            .findFirst()
                            .map(Instruction::getValue)
                            .get();

                    this.value = (short) (valueVar1 << Integer.valueOf(var2));

                } else if (this.toDo.split(" ")[1].equals("RSHIFT")) {
                    // kk RSHIFT 3 -> km
                    String var1 = this.toDo.split(" ")[0];
                    String var2 = this.toDo.split(" ")[2];

                    short valueVar1 = instructions.stream()
                            .filter(inst -> inst.getResult().equals(var1))
                            .findFirst()
                            .map(Instruction::getValue)
                            .get();

                    // En java on ne peut right shift que des int. Probleme ils font 32 bits et nous on veut que 16
                    // Si on fait un shift normal, il risque d'y avoir des "1" qui se glissent à gauche au lieu de "0" (dépend de la valeur de départ)
                    // Pour contrer le problème, on fait 16 left shifts, puis 16 + x right shifts. Ainsi on est sur d'avoir que des 0 à gauche
                    int temp = valueVar1 << 16;
                    this.value = (short)(temp >>> 16 + Integer.parseInt(var2));

                } else {
                    System.out.println("Unknown instruction : " + toDo);
                    return 0;
                }
            }
            // System.out.println(this.result + " = " + this.value);
            return this.value;
        }
    }

    // List de stockage des instructions
    private List<Instruction> instructions;

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(7);

        String valueWanted = "a";

        instructions = new ArrayList<>();
        for (String inst : entries) {
            instructions.add(new Instruction(inst.split(" -> ")[0], inst.split(" -> ")[1]));
        }

        Short value = instructions.stream()
                .filter(inst -> inst.getResult().equals(valueWanted))
                .findFirst()
                .map(Instruction::getValue)
                .get();
        System.out.println("Signal provided to " + valueWanted + " : " + value);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(7);

        String valueWanted = "a";

        instructions = new ArrayList<>();
        for (String inst : entries) {
            instructions.add(new Instruction(inst.split(" -> ")[0], inst.split(" -> ")[1]));
        }

        // On modifie l'instruction pour b
        instructions.stream()
                .filter(inst -> inst.getResult().equals("b"))
                .forEach(inst -> inst.setToDo("3176"));

        Short value = instructions.stream()
                .filter(inst -> inst.getResult().equals(valueWanted))
                .findFirst()
                .map(Instruction::getValue)
                .get();
        System.out.println("Signal provided to " + valueWanted + " : " + value);

    }
}
