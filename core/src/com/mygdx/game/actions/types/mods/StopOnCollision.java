package com.mygdx.game.actions.types.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.AdvancedIntersector;
import com.mygdx.game.AlignmentPack;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.Comparator;
import java.util.List;

public class StopOnCollision implements Mod{

    public StopOnCollision() {

    }

    private static void stopOnCollision(Vector2 vector,Attackable master, List<Health> healths){
        Vector2 intersection=new Vector2();
        AlignmentPack alignmentPack = new AlignmentPack();
        healths.stream()
                .sorted(Comparator.comparing(health -> health.getCenter().dst(master.getCenter())))
                .filter(other -> master.getId() != other.getId())
                .filter(health -> AdvancedIntersector.intersectSegmentRectangle(
                        master.getCenter(),vector,health.getBounds(),intersection,alignmentPack))
                .findFirst()
                .ifPresent(other->vector.set(intersection));
    }

    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        stopOnCollision(cursor,master,mainPool.get(Health.class));
    }
}
