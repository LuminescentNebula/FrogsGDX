package com.mygdx.game.actions.types.mods;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.interfaces.Attackable;

/**
 * Перемещает vector относительно origin на translation
 *<a href="https://www.geogebra.org/m/enryxfpm">Example</a>
 * <p> .x Указывает перемещение по касательной,
 * <p> .y Указывает перемещение от центра и к центру
 *
 */
public class TranslateRotated extends Translate{

    public TranslateRotated(float x, float y) {
        super(x, y);
    }

    private static void translateRotated(Vector2 vector, Vector2 origin, float x, float y){
        float angle = (float) Math.atan2(vector.y - origin.y, vector.x - origin.x);
        vector.set((float) (vector.x-x*Math.sin(angle)+y*Math.sin(angle)),
                (float) (vector.y+x*Math.cos(angle)+y*Math.sin(angle)));
    }

    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        translateRotated(cursor,master.getCenter(),x,y);
    }
}
