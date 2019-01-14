package fr.rochet.days;

import fr.rochet.DayInterface;
import fr.rochet.ExoUtils;

import java.io.IOException;

/**
 * --- Day 22: Wizard Simulator 20XX ---
 * <p>
 * Little Henry Case decides that defeating bosses with swords and stuff is boring. Now he's playing the game with a wizard. Of course, he gets stuck on another boss and needs your help again.
 * <p>
 * In this version, combat still proceeds with the player and the boss taking alternating turns. The player still goes first. Now, however, you don't get any equipment;
 * instead, you must choose one of your spells to cast. The first character at or below 0 hit points loses.
 * <p>
 * Since you're a wizard, you don't get to wear armor, and you can't attack normally. However, since you do magic damage, your opponent's armor is ignored, and so the boss effectively has zero armor as well.
 * As before, if armor (from a spell, in this case) would reduce damage below 1, it becomes 1 instead - that is, the boss' attacks always deal at least 1 damage.
 * <p>
 * On each of your turns, you must select one of your spells to cast. If you cannot afford to cast any spell, you lose. Spells cost mana; you start with 500 mana, but have no maximum limit.
 * You must have enough mana to cast a spell, and its cost is immediately deducted when you cast it. Your spells are Magic Missile, Drain, Shield, Poison, and Recharge.
 * <p>
 * Magic Missile costs 53 mana. It instantly does 4 damage.
 * Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
 * Shield costs 113 mana. It starts an effect that lasts for 6 turns. While it is active, your armor is increased by 7.
 * Poison costs 173 mana. It starts an effect that lasts for 6 turns. At the start of each turn while it is active, it deals the boss 3 damage.
 * Recharge costs 229 mana. It starts an effect that lasts for 5 turns. At the start of each turn while it is active, it gives you 101 new mana.
 * <p>
 * Effects all work the same way. Effects apply at the start of both the player's turns and the boss' turns. Effects are created with a timer (the number of turns they last);
 * at the start of each turn, after they apply any effect they have, their timer is decreased by one. If this decreases the timer to zero, the effect ends.
 * You cannot cast a spell that would start an effect which is already active. However, effects can be started on the same turn they end.
 * <p>
 * For example, suppose the player has 10 hit points and 250 mana, and that the boss has 13 hit points and 8 damage:
 * <p>
 * -- Player turn --
 * - Player has 10 hit points, 0 armor, 250 mana
 * - Boss has 13 hit points
 * Player casts Poison.
 * <p>
 * -- Boss turn --
 * - Player has 10 hit points, 0 armor, 77 mana
 * - Boss has 13 hit points
 * Poison deals 3 damage; its timer is now 5.
 * Boss attacks for 8 damage.
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 0 armor, 77 mana
 * - Boss has 10 hit points
 * Poison deals 3 damage; its timer is now 4.
 * Player casts Magic Missile, dealing 4 damage.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 0 armor, 24 mana
 * - Boss has 3 hit points
 * Poison deals 3 damage. This kills the boss, and the player wins.
 * <p>
 * Now, suppose the same initial conditions, except that the boss has 14 hit points instead:
 * <p>
 * -- Player turn --
 * - Player has 10 hit points, 0 armor, 250 mana
 * - Boss has 14 hit points
 * Player casts Recharge.
 * <p>
 * -- Boss turn --
 * - Player has 10 hit points, 0 armor, 21 mana
 * - Boss has 14 hit points
 * Recharge provides 101 mana; its timer is now 4.
 * Boss attacks for 8 damage!
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 0 armor, 122 mana
 * - Boss has 14 hit points
 * Recharge provides 101 mana; its timer is now 3.
 * Player casts Shield, increasing armor by 7.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 7 armor, 110 mana
 * - Boss has 14 hit points
 * Shield's timer is now 5.
 * Recharge provides 101 mana; its timer is now 2.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 1 hit point, 7 armor, 211 mana
 * - Boss has 14 hit points
 * Shield's timer is now 4.
 * Recharge provides 101 mana; its timer is now 1.
 * Player casts Drain, dealing 2 damage, and healing 2 hit points.
 * <p>
 * -- Boss turn --
 * - Player has 3 hit points, 7 armor, 239 mana
 * - Boss has 12 hit points
 * Shield's timer is now 3.
 * Recharge provides 101 mana; its timer is now 0.
 * Recharge wears off.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 2 hit points, 7 armor, 340 mana
 * - Boss has 12 hit points
 * Shield's timer is now 2.
 * Player casts Poison.
 * <p>
 * -- Boss turn --
 * - Player has 2 hit points, 7 armor, 167 mana
 * - Boss has 12 hit points
 * Shield's timer is now 1.
 * Poison deals 3 damage; its timer is now 5.
 * Boss attacks for 8 - 7 = 1 damage!
 * <p>
 * -- Player turn --
 * - Player has 1 hit point, 7 armor, 167 mana
 * - Boss has 9 hit points
 * Shield's timer is now 0.
 * Shield wears off, decreasing armor by 7.
 * Poison deals 3 damage; its timer is now 4.
 * Player casts Magic Missile, dealing 4 damage.
 * <p>
 * -- Boss turn --
 * - Player has 1 hit point, 0 armor, 114 mana
 * - Boss has 2 hit points
 * Poison deals 3 damage. This kills the boss, and the player wins.
 * <p>
 * You start with 50 hit points and 500 mana points. The boss's actual stats are in your puzzle input. What is the least amount of mana you can spend and still win the fight?
 * (Do not include mana recharge effects as "spending" negative mana.)
 * <p>
 * Your puzzle answer was 900.
 * --- Part Two ---
 * <p>
 * On the next run through the game, you increase the difficulty to hard.
 * <p>
 * At the start of each player turn (before any other effects apply), you lose 1 hit point. If this brings you to or below 0 hit points, you lose.
 * <p>
 * With the same starting stats for you and the boss, what is the least amount of mana you can spend and still win the fight?
 */
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
        if (yourTurn) {
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

                        if (copyYou.loseMana(Spell.SHIELD.manaCost) && copyYou.startShield(7, 6)) {
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.SHIELD.manaCost);
                        }
                    } else if (spell == Spell.POISON) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.POISON.manaCost) && copyBoss.startPoison(3, 6)) {
                            nextTurn(false, copyYou, copyBoss, manaSpent + Spell.POISON.manaCost);
                        }
                    } else if (spell == Spell.RECHARGE) {
                        Perso copyYou = you.clone();
                        Perso copyBoss = boss.clone();

                        if (copyYou.loseMana(Spell.RECHARGE.manaCost) && copyYou.startRecharge(101, 5)) {
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
    }
}
