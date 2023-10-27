package com.mygdx.game.actions.types.mods;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.Attackable;

public class Rotate implements Mod{
    float angle;

    public Rotate(float angle) {
        this.angle = MathUtils.degreesToRadians*angle;
    }

    private static void rotate(Vector2 vector, Vector2 origin, float angle) {
        float cosAngle = MathUtils.cos(angle);
        float sinAngle = MathUtils.sin(angle);

        float deltaX = vector.x - origin.x;
        float deltaY = vector.y - origin.y;

        float rotatedX = deltaX * cosAngle - deltaY * sinAngle;
        float rotatedY = deltaY * cosAngle + deltaX * sinAngle;

        vector.x = origin.x + rotatedX;
        vector.y = origin.y + rotatedY;
    }


    @Override
    public void apply(Attackable master, Vector2 cursor, MainPool mainPool) {
        rotate(cursor,master.getCenter(),angle);
    }
}
