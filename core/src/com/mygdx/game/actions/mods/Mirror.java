package com.mygdx.game.actions.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.Attackable;

public class Mirror implements Mod{
    public static boolean MIRROR_X=true;
    public static boolean MIRROR_Y=false;

    private boolean side;

    public Mirror(boolean side) {
        this.side=side;
    }

    private static void mirror(Vector2 vector, Vector2 origin, boolean side) {
        if (side) {
            vector.set((2 * origin.x) - vector.x, vector.y);
        } else {
            vector.set(vector.x, (2 * origin.y) - vector.y);
        }
    }

    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        mirror(cursor,master.getCenter(),side);
    }
}
