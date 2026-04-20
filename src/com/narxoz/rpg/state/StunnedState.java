package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class StunnedState implements HeroState {
    private int turnsRemaining = 1;

    @Override
    public String getName() {
        return "Stunned";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return 0;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return (int) (rawDamage * 1.1);
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(">>> " + hero.getName() + " is stunned and can't move!");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(">>> " + hero.getName() + " is no longer stunned.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return false; 
    }
}