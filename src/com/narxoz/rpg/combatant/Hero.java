package com.narxoz.rpg.combatant;

import com.narxoz.rpg.state.HeroState;
import com.narxoz.rpg.state.NormalState;

public class Hero {
    private final String name;
    private int hp;
    private final int maxHp;
    private final int attackPower;
    private final int defense;
    private HeroState state;

    public Hero(String name, int hp, int attackPower, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.state = new NormalState();
    }

    public void setState(HeroState state) {
        this.state = state;
    }

    public HeroState getState() {
        return state;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public boolean isAlive() { return hp > 0; }

    public void takeDamage(int amount) {
        int modifiedDamage = state.modifyIncomingDamage(amount);
        hp = Math.max(0, hp - modifiedDamage);
    }

    public void startTurn() {
        state.onTurnStart(this);
    }

    public void endTurn() {
        state.onTurnEnd(this);
    }

    public void heal(int amount) {
        hp = Math.min(maxHp, hp + amount);
    }
}
