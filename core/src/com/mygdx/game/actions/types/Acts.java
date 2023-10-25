package com.mygdx.game.actions.types;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.ActInterface;
import com.mygdx.game.interfaces.Health;

public abstract  class  Acts {

    public static ActInterface[] acts = new ActInterface[] {
            Acts::checkLine,
            Acts::checkCircle,
            Acts::checkRange
    };

    public static boolean checkLine(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        return Intersector.intersectSegmentRectangle(master, cursor, other.getBounds());
    }
    public static boolean checkCircle(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        return Intersector.overlaps(circle, other.getBounds());
    }
    public static boolean checkRange(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        circle.setPosition(master);
        return checkCircle(other, master, cursor, circle);
    }
}
