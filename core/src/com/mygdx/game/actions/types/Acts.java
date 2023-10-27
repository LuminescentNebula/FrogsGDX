package com.mygdx.game.actions.types;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.ActInterface;
import com.mygdx.game.interfaces.Health;

public abstract  class  Acts {

    public static ActInterface[] acts = new ActInterface[] {
            Acts::checkLine,
            Acts::checkCircle,
            Acts::checkRange,
            Acts::checkCone,
            Acts::checkPoint
    };

    private static boolean checkPoint(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        return other.getBounds().contains(cursor);
    }

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
    public static boolean checkCone(Health other, Vector2 master, Vector2 cursor, Circle circle){
        float angle = Draws.getAngle(master, cursor);

        Vector2 direction = cursor.cpy().sub(master);
        cursor.set(master.cpy().add(direction));
        double v2 = (cursor.dst(master)*Math.tan(Math.toRadians(circle.radius)/2));

        circle = new Circle(master,cursor.dst(master)+1);

        Polygon tri = new Polygon(new float[]{
                master.x, master.y,
                (float) (cursor.x + v2 * Math.sin(angle)),
                (float) (cursor.y - v2 * Math.cos(angle)),
                (float) (cursor.x - v2 * Math.sin(angle)),
                (float) (cursor.y + v2 * Math.cos(angle))
        });
        for (Vector2 i: new Vector2[]{new Vector2(other.getBounds().x,other.getBounds().y),
                new Vector2(other.getBounds().x+other.getBounds().width,other.getBounds().y),
                new Vector2(other.getBounds().x+other.getBounds().width,other.getBounds().y+other.getBounds().height),
                new Vector2(other.getBounds().x,other.getBounds().y+other.getBounds().height)}) {
            if (circle.contains(i) && tri.contains(i)) {
                return true;
            }
        }
        return false;
    }


}
