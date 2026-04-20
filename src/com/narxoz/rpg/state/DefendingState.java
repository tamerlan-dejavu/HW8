package com.narxoz.rpg.state;

import com.narxoz.rpg.combatant.Hero;

public class DefendingState implements HeroState {
    private int turnsRemaining = 2; 

    @Override
    public String getName() {
        return "Defending";
    }

    @Override
    public int modifyOutgoingDamage(int basePower) {
        return basePower / 2;
    }

    @Override
    public int modifyIncomingDamage(int rawDamage) {
        return rawDamage / 2;
    }

    @Override
    public void onTurnStart(Hero hero) {
        System.out.println(">>> " + hero.getName() + " raises their shield! (Duration: " + turnsRemaining + ")");
    }

    @Override
    public void onTurnEnd(Hero hero) {
        turnsRemaining--;
        if (turnsRemaining <= 0) {
            System.out.println(">>> " + hero.getName() + " lowers their shield.");
            hero.setState(new NormalState());
        }
    }

    @Override
    public boolean canAct() {
        return true;
    }
}