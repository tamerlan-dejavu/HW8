package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class BerserkState implements HeroState {
    private int turnsRemaining = 3;

    @Override
    public String getName() {
        return "Berserk";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return (int) (basePower * 1.5);
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.25);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(hero.getName() + " is foaming at the mouth in a berserker rage!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(">>> The rage fades. " + hero.getName() + " returns to normal.");
            hero.setState(new NormalState()); 
        } else {
            int exhaustionDamage = 2;
            hero.takeDamage(exhaustionDamage);
            System.out.println(">>> Berserk exhaustion: " + hero.getName() + " loses " + exhaustionDamage + " HP.");
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}