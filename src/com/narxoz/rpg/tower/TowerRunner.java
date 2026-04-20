package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.FloorResult;
import java.util.List;

public class TowerRunner {
    private final List<Hero> party;
    private final List<TowerFloor> floors;
    private int floorsCleared;
    private int currentFloor;

    public TowerRunner(List<Hero> party, List<TowerFloor> floors) {
        this.party = party;
        this.floors = floors;
        this.floorsCleared = 0;
        this.currentFloor = 0;
    }

    public TowerRunResult execute() {
        System.out.println("\n" + "==============================================");
        System.out.println("TOWER CLIMB BEGINS");
        System.out.println("Party: " + party.size() + " heroes | Tower height: " + floors.size() + " floors");
        System.out.println("==============================================");

        for (TowerFloor floor : floors) {
            currentFloor++;
            System.out.println("\n" + "==============================================");
            System.out.println("FLOOR " + currentFloor + " of " + floors.size());
            System.out.println("==============================================");

            if (!party.stream().anyMatch(Hero::isAlive)) {
                System.out.println("\nAll heroes have fallen! Tower climb over.");
                break;
            }

            FloorResult result = floor.explore(party);

            if (result.isCleared()) {
                floorsCleared++;
                System.out.println("\nFloor cleared! Total: " + floorsCleared + "/" + floors.size());
            } else {
                System.out.println("\nFloor failed!");
            }

            int survivors = (int) party.stream().filter(Hero::isAlive).count();
            System.out.println("Heroes alive: " + survivors + "/" + party.size());

            if (!party.stream().anyMatch(Hero::isAlive)) {
                System.out.println("\nAll heroes have fallen! Tower climb over.");
                break;
            }
        }

        return createResult();
    }

    private TowerRunResult createResult() {
        int heroesSurviving = (int) party.stream().filter(Hero::isAlive).count();
        boolean reachedTop = floorsCleared == floors.size();

        System.out.println("\n" + "==============================================");
        System.out.println("TOWER CLIMB SUMMARY");
        System.out.println("==============================================");
        System.out.println("Floors cleared: " + floorsCleared + "/" + floors.size());
        System.out.println("Heroes surviving: " + heroesSurviving + "/" + party.size());
        System.out.println("Reached top: " + (reachedTop ? "YES" : "NO"));
        System.out.println("==============================================");

        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }

    public int getFloorsCleared() { return floorsCleared; }
    public int getCurrentFloor() { return currentFloor; }
}
