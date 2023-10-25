package com.mygdx.game.actions.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.Attackable;

public class Translate implements Mod{
    float x,y;

    public Translate(float x,float y) {
        this.x=x;
        this.y=y;
    }

    private static void translate(Vector2 vector, float x, float y) {
        vector.set(vector.add(x,y));
    }

    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        translate(cursor,x,y);
    }
}
