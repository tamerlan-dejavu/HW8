package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.CombatFloor;
import com.narxoz.rpg.floor.RestFloor;
import com.narxoz.rpg.floor.TrapFloor;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.NormalState;
import com.narxoz.rpg.tower.TowerRunner;
import com.narxoz.rpg.tower.TowerRunResult;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Hero> party = createParty();
        List<Object> floors = createTower();
        TowerRunner runner = new TowerRunner(party, (List) floors );
        TowerRunResult result = runner.execute();

        System.out.println("==============================================");
        System.out.println("FINAL TOWER RUN STATISTICS");
        System.out.println("==============================================");
        System.out.println("Floors Cleared: " + result.getFloorsCleared());
        System.out.println("Heroes Surviving: " + result.getHeroesSurviving());
        System.out.println("Tower Conquered: " + (result.isReachedTop() ? "YES" : "NO"));
        System.out.println("==============================================");
    }

    private static List<Hero> createParty() {
        List<Hero> party = new ArrayList<>();

        Hero hero1 = new Hero("Aragorn", 100, 15, 8);
        hero1.setState(new NormalState());
        party.add(hero1);

        Hero hero2 = new Hero("Legolas", 80, 18, 6);
        hero2.setState(new BerserkState());
        party.add(hero2);

        System.out.println("Party Created:");
        System.out.println("  - " + hero1.getName() + " (HP: " + hero1.getHp() + "/" + hero1.getMaxHp() + ", ATK: " + hero1.getAttackPower() + ", DEF: " + hero1.getDefense() + ") [State: " + hero1.getState().getName() + "]");
        System.out.println("  - " + hero2.getName() + " (HP: " + hero2.getHp() + "/" + hero2.getMaxHp() + ", ATK: " + hero2.getAttackPower() + ", DEF: " + hero2.getDefense() + ") [State: " + hero2.getState().getName() + "]");
        return party;
    }

    private static List<Object> createTower() {
        List<Object> floors = new ArrayList<>();

        floors.add(new CombatFloor(2, 30, 8));
        floors.add(new RestFloor(25));
        floors.add(new TrapFloor(15));
        floors.add(new CombatFloor(3, 35, 10));
        floors.add(new RestFloor(30));

        System.out.println("\nTower Structure:");
        System.out.println("1. Combat Floor (2 Goblins)");
        System.out.println("2. Rest Chamber (Healing)");
        System.out.println("3. Trap Corridor (Deadly traps)");
        System.out.println("4. Combat Floor (3 Goblins)");
        System.out.println("5. Rest Chamber (Final healing)");
        return floors;
    }
}
