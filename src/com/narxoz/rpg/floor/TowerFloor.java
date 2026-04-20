package com.narxoz.rpg.floor;

import com.narxoz.rpg.combatant.Hero;
import java.util.List;

/**
 * Abstract base class for tower floors, using the Template Method pattern.
 *
 * The public explore() method defines the fixed skeleton for exploring a floor:
 *   1. announce()            — print floor introduction (can be overridden)
 *   2. setup(party)          — prepare the floor (abstract; subclass implements)
 *   3. resolveChallenge()    — conduct the floor's challenge (abstract; subclass implements)
 *   4. awardLoot()           — grant rewards if applicable (abstract; subclass implements)
 *   5. cleanup(party)        — clean up after the floor (optional hook; can be overridden)
 *
 * Subclasses must:
 * - Implement all abstract methods: getFloorName(), setup(), resolveChallenge(), awardLoot()
 * - NEVER override explore() — it is final
 * - Optionally override the hooks: announce(), shouldAwardLoot(), cleanup()
 *
 * This is the Template Method pattern in action: the algorithm skeleton is fixed in the
 * final method, and subclasses customize the details.
 */
public abstract class TowerFloor {
    public final FloorResult explore(List<Hero> party) {
        announce();
        setup(party);
        FloorResult result = resolveChallenge(party);
        if (shouldAwardLoot(result)) {
            awardLoot(party, result);
        }
        cleanup(party);
        return result;
    }

    protected void announce() {
        System.out.println("\n--- Entering " + getFloorName() + " ---");
    }

    protected abstract void setup(List<Hero> party);

    protected abstract FloorResult resolveChallenge(List<Hero> party);

    protected boolean shouldAwardLoot(FloorResult result) {
        return true;
    }

    protected abstract void awardLoot(List<Hero> party, FloorResult result);

    protected void cleanup(List<Hero> party) {
        
    }

    protected abstract String getFloorName();
}
