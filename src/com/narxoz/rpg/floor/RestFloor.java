package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;

public class RestFloor extends TowerFloor {
    private final int healAmount;

    public RestFloor(int healAmount) {
        this.healAmount = healAmount;
    }

    @Override
    protected String getFloorName() {
        return "Rest Chamber";
    }

    @Override
    protected void announce() {
        System.out.println("\n---  A Peaceful Rest Chamber Awaits ---");
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The chamber is safe and warm. Time to recover");
        for (Hero hero : party) {
            hero.startTurn();
        }
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        System.out.println("\nSimply exist peacefully (no combat)");
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(healAmount);
                System.out.println(hero.getName() + " rests and recovers " + healAmount + " HP (now " + hero.getHp() + "/" + hero.getMaxHp() + ")");
            }
            hero.endTurn();
        }
        System.out.println("Everyone is refreshed");
        return new FloorResult(true, 0, "Rest completed successfully");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        System.out.println("No treasure in a rest chamber - skipping loot phase");
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
    }

    @Override
    protected void cleanup(List<Hero> party) {
        System.out.println("Heroes leave the chamber, refreshed and ready");
    }
}
