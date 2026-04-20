package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;
import java.util.Random;

public class TrapFloor extends TowerFloor {
    private final int baseTrapDamage;
    private final Random random;
    private int trapsDodged;

    public TrapFloor(int baseTrapDamage) {
        this.baseTrapDamage = baseTrapDamage;
        this.random = new Random();
        this.trapsDodged = 0;
    }

    @Override
    protected String getFloorName() {
        return "Trap Corridor";
    }

    @Override
    protected void announce() {
        System.out.println("\n---  DANGER! Traps Detected Ahead ---");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The corridor is filled with pressure plates, spikes, and poisoned darts");
        trapsDodged = 0;
        for (Hero hero : party) {
            hero.startTurn();
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int totalDamageTaken = 0;
        System.out.println("\nNavigating the deadly corridor\n");
        for (int trapIdx = 1; trapIdx <= 3; trapIdx++) {
            System.out.println(">>> Trap " + trapIdx + " Triggered ---");

            for (Hero hero : party) {
                if (!hero.isAlive()) {
                    continue;
                }

                int dodgeChance = 30;
                if (random.nextInt(100) < dodgeChance) {
                    System.out.println(hero.getName() + " dodges the trap");
                    trapsDodged++;
                } else {
                    int damage = baseTrapDamage + random.nextInt(10);
                    hero.takeDamage(damage);
                    System.out.println(hero.getName() + " is hit! Takes " + damage + " damage (HP: " + hero.getHp() + ")");
                    totalDamageTaken += damage;
                }
            }

            for (Hero hero : party) {
                hero.endTurn();
            }
        }

        boolean cleared = party.stream().anyMatch(Hero::isAlive);
        String summary = cleared ? "Survived " + trapsDodged + " dodges and made it through" : "All heroes fell to the traps";
        System.out.println(summary);
        return new FloorResult(cleared, totalDamageTaken, summary);
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        boolean award = result.isCleared() && trapsDodged >= 2;
        if (!award) {
            System.out.println("Not enough dodges (" + trapsDodged + " < 2) - no treasure found");
        }
        return award;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("\nFound hidden treasure among the traps");
        int lootHeal = 30;
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(lootHeal);
                System.out.println(hero.getName() + " healed for " + lootHeal + " HP (now " + hero.getHp() + "/" + hero.getMaxHp() + ")");
            }
        }
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("Disabled the remaining trap mechanisms");
    }
}
