package com.mygdx.game.actions.types.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.List;

public class StickToTargets implements Mod {

    public StickToTargets() {

    }

    public static void stickToTargets(Vector2 vector, List<Health> healths) {
        healths.stream()
                .filter(health -> health.getBounds().contains(vector))
                .findFirst()
                .ifPresent(health->vector.set(health.getCenter()));
    }

    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        stickToTargets(cursor,mainPool.get(Health.class));
    }
}

