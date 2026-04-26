package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.state.DefendingState;
import java.util.ArrayList;
import java.util.List;

public class CombatFloor extends TowerFloor {
    private List<Monster> monsters;
    private final int monsterCount;
    private final int monsterHp;
    private final int monsterAttack;

    public CombatFloor(int monsterCount, int monsterHp, int monsterAttack) {
        this.monsterCount = monsterCount;
        this.monsterHp = monsterHp;
        this.monsterAttack = monsterAttack;
    }

    @Override
    protected String getFloorName() {
        return "Combat Floor (Goblins)";
    }

    @Override
    protected void announce() {
        System.out.println("\n--- COMBAT! " + monsterCount + " Goblins Ahead ---");
    }

    @Override
    protected void setup(List<Hero> party) {
        monsters = new ArrayList<>();
        for (int i = 0; i < monsterCount; i++) {
            monsters.add(new Monster("Goblin " + (i + 1), monsterHp, monsterAttack));
        }
        System.out.println(monsterCount + " goblins spawn!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamageTaken = 0;

        System.out.println("\nCombat Begins");

        int round = 0;
        while (!monsters.isEmpty() && party.stream().anyMatch(Hero::isAlive)) {
            round++;
            System.out.println("\n--- Round " + round + " ---");

            for (Hero hero : party) {
                hero.startTurn();
            }

            for (Hero hero : party) {
                if (!hero.isAlive() || !hero.canAct()) {
                    if (hero.isAlive() && !hero.canAct()) {
                        System.out.println(hero.getName() + " cannot act (" + hero.getState().getName() + ")");
                    }
                    continue;
                }

                Monster target = monsters.stream().filter(Monster::isAlive).findFirst().orElse(null);

                if (target != null) {
                    int baseDamage = hero.getAttackPower();
                    int modifiedDamage = hero.calculateAttack();
                    target.takeDamage(modifiedDamage);
                    System.out.println(hero.getName() + " attacks " + target.getName() + " for " + modifiedDamage + " damage (base: " + baseDamage + ")");
                    if (!target.isAlive()) {
                        System.out.println("-> " + target.getName() + " defeated!");
                    }
                }
            }

            monsters.removeIf(m -> !m.isAlive());

            for (Monster monster : monsters) {
                for (Hero hero : party) {
                    if (hero.isAlive()) {
                        int damage = Math.max(1, monster.getAttackPower() - 2);
                        hero.takeDamage(damage);
                        System.out.println(monster.getName() + " attacks " + hero.getName() + " for " + damage + " damage. " + hero.getName() + " HP: " + hero.getHp());
                        totalDamageTaken += damage;

                        if (hero.isAlive() && hero.getHp() < hero.getMaxHp() * 0.4 && !(hero.getState() instanceof DefendingState)) {
                            hero.setState(new DefendingState());
                            System.out.println(">>> " + hero.getName() + " is badly wounded and takes a defensive stance! [State: Defending]");
                        }
                        break;
                    }
                }
            }

            for (Hero hero : party) {
                hero.endTurn();
            }
        }

        boolean cleared = monsters.isEmpty() && party.stream().anyMatch(Hero::isAlive);
        String summary = cleared ? "All goblins defeated!" : "Heroes fell in combat!";

        System.out.println(summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (result.isCleared()) {
            int lootHeal = 20;
            System.out.println("\nHeroes gain 20 HP and victory!");
            for (Hero hero : party) {
                if (hero.isAlive()) {
                    hero.heal(lootHeal);
                    System.out.println(hero.getName() + " healed for " + lootHeal + " HP (now " + hero.getHp() + "/" + hero.getMaxHp() + ")");
                }
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        monsters.clear();
        System.out.println("Floor cleared of monsters.");
    }
}
