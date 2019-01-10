package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

public class Day22 implements DayInterface {

    // Pour faire plus rigolo, on va faire 1 seul classe "Personnage". On aurait pu en faire 2 pour leur donner à chacun des attributs particuliers pour l'exo (ex pas de poison sur notre perso).
    // On va imaginer que le jeu pourra évoluer et que les 2 persos pourront avoir les memes effets.
    private class Perso {
        int pv;
        int damages;
        int mana;
        int armor;
        int shield;
        int shieldTimer;
        int poison;
        int poisonTimer;
        int recharge;
        int rechargeTimer;

        Perso() {
        }

        Perso(int pv, int damages, int mana, int armor) {
            this.pv = pv;
            this.damages = damages;
            this.mana = mana;
            this.armor = armor;
            this.shield = 0;
            this.shieldTimer = 0;
            this.poison = 0;
            this.poisonTimer = 0;
            this.recharge = 0;
            this.rechargeTimer = 0;
        }

        void gainPv(int add) {
            this.pv += add;
        }

        void losePv(int loss) {
            this.pv -= loss;
        }

        boolean dead() {
            return this.pv <= 0;
        }

        int getDamages() {
            return damages;
        }

        // Permet de vérifier si on a assez de mana avant d'attaquer et donc de bloquer l'attaque (et le schéma de combat) si impossible
        boolean loseMana(int loss) {
            if (loss > this.mana) {
                return false;
            }
            this.mana -= loss;
            return true;
        }

        int getArmor() {
            return armor;
        }

        void applyEffects() {
            if (this.shieldTimer > 0) {
                this.shieldTimer--;
                if (this.shieldTimer == 0) {
                    this.armor -= shield;
                }
            }
            if (this.poisonTimer > 0) {
                this.pv -= poison;
                this.poisonTimer--;
            }
            if (this.rechargeTimer > 0) {
                this.mana += recharge;
                this.rechargeTimer--;
            }
        }

        boolean startShield(int power, int duration) {
            if (this.shieldTimer <= 0) {
                this.armor += power; // on ajoute le shield à l'armure
                this.shield = power; // mais on garde la valeur en mémoire pour l'enlever à la fin
                this.shieldTimer = duration;
                return true;
            } else {
                return false;
            }
        }

        boolean startPoison(int power, int duration) {
            if (this.poisonTimer <= 0) {
                this.poison = power;
                this.poisonTimer = duration;
                return true;
            } else {
                return false;
            }
        }

        boolean startRecharge(int power, int duration) {
            if (this.rechargeTimer <= 0) {
                this.recharge = power;
                this.rechargeTimer = duration;
                return true;
            } else {
                return false;
            }
        }

        protected Perso clone() {
            Perso clone = new Perso();
            clone.pv = this.pv;
            clone.damages = this.damages;
            clone.mana = this.mana;
            clone.armor = this.armor;
            clone.shield = this.shield;
            clone.shieldTimer = this.shieldTimer;
            clone.poison = this.poison;
            clone.poisonTimer = this.poisonTimer;
            clone.recharge = this.recharge;
            clone.rechargeTimer = this.rechargeTimer;
            return clone;
        }
    }

    private enum Spell {
        MAGIC_MISSILE(53),
        DRAIN(73),
        SHIELD(113),
        POISON(173),
        RECHARGE(229);

        int manaCost;

        Spell(int manaCost) {
            this.manaCost = manaCost;
        }
    }

    // Pour tester toutes les combinaisons de jeu possible on va tout simplement utiliser notre amie la récursivité
    private void nextTurn(boolean yourTurn, Perso you, Perso boss, int manaSpent) {

        // Part 2 : à commenter pour faire la partie 1
        if (yourTurn){
            you.losePv(1);
        }
        // Fin de partie à commenter

        // Avant de faire qqchose : on vérifie si on a dépassé le min de mana qu'on a obtenu
        // Si on a dépassé on peut déjà arreter
        if (manaSpent >= minManaUsed) {
            return;
        }

        you.applyEffects();
        boss.applyEffects();

        // On vérifie que le boss n'est pas mort d'un effet ou du coup précédent
        if (boss.dead()) {
            if (manaSpent < minManaUsed) {
                minManaUsed = manaSpent;
            }
        }

        // On vérifie que le joueur n'est pas mort non plus, plus utile de continuer si c'est le cas
        if (!you.dead()) {
            if (yourTurn) {
                for (Spell spell : Spell.values()) {
                    if (spell == Spell.MAGIC_MISSILE) {
                        // on fait une copie des états des joueurs pour pas additionner tous les effets
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.MAGIC_MISSILE.manaCost)) {
                            copyBoss.losePv(4);
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.MAGIC_MISSILE.manaCost);
                        }
                    } else if (spell == Spell.DRAIN) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.DRAIN.manaCost)) {
                            copyYou.gainPv(2);
                            copyBoss.losePv(2);
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.DRAIN.manaCost);
                        }
                    } else if (spell == Spell.SHIELD) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.SHIELD.manaCost)) {
                            copyYou.startShield(7, 6);
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.SHIELD.manaCost);
                        }
                    } else if (spell == Spell.POISON) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.POISON.manaCost)) {
                            copyBoss.startPoison(3, 6);
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.POISON.manaCost);
                        }
                    } else if (spell == Spell.RECHARGE) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.RECHARGE.manaCost)) {
                            copyYou.startRecharge(101, 5);
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.RECHARGE.manaCost);
                        }
                    }
                }
            } else {
                int damagesReceived = boss.getDamages() - you.getArmor() > 0 ? boss.getDamages() - you.getArmor() : 1;
                you.losePv(damagesReceived);

                if (!you.dead()) {
                    nextTurn(true, you, boss, manaSpent);
                }
            }
        }
    }

    private int minManaUsed;

    @Override
    public void part1() throws IOException {
        String[] entries = ExoUtils.getEntries(22);

        Perso you = new Perso(50, 0, 500, 0);
        Perso boss = new Perso(
                Integer.parseInt(entries[0].split(" ")[2]),
                Integer.parseInt(entries[1].split(" ")[1]),
                0,
                0
        );

        minManaUsed = Integer.MAX_VALUE;
        nextTurn(true, you, boss, 0);

        System.out.println("The minimum mana you have to use to defeat boss is : " + minManaUsed);
    }

    @Override
    public void part2() throws IOException {
        String[] entries = ExoUtils.getEntries(22);

    }
}
