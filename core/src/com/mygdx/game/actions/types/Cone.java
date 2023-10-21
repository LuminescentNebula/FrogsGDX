package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.interfaces.Health;

public class Cone extends Type {//Конус
    //FIXME: IMPLEMENT ME!!
    float radius,angle;
    protected final static float coneArcSize = 53;
    

    @Override
    public boolean check(Health other, Vector2 master, Vector2 cursor, Circle circle) {
        circle = new Circle(master,radius+1);

        Vector2 direction = cursor.cpy().sub(master);
        direction.limit(350);
        cursor.set(master.cpy().add(direction));
        double v2 = (radius*Math.tan(Math.toRadians(coneArcSize)/2));

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

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        Vector2 point1 = new Vector2(cursor);
        Vector2 point2 = new Vector2(master.x + master.dst(cursor), master.y);
        point1.sub(master).nor();
        point2.sub(master).nor();
        float angle = (MathUtils.atan2(point1.y, point1.x) - MathUtils.atan2(point2.y, point2.x));

        Vector2 direction = cursor.cpy().sub(master);
        if (minLength != 0 && maxLength != 0) {
            direction.clamp(minLength, maxLength);
        } else if (maxLength != 0) {
            direction.limit(maxLength);
        }
        cursor.set(master.cpy().add(direction));
        radius = cursor.dst(master);
        this.radius = radius;
        this.angle = angle;

        shapeRenderer.setColor(Attack.areaColor);
        shapeRenderer.arc(
                master.x, master.y,
                cursor.dst(master),
                angle * MathUtils.radiansToDegrees - coneArcSize / 2, coneArcSize);

        //double v = radius * Math.sin(Math.toRadians(coneArcSize) / 2);
        //double v1 = radius * (1 - Math.cos(Math.toRadians(coneArcSize) / 2));
//        double v2 = (radius*Math.tan(Math.toRadians(coneArcSize)/2));
//        shapeRenderer.triangle(
//                master.x, master.y,
//                (float) (cursor.x
//                        + v2 * Math.sin(angle)
////                        - v * Math.sin(angle)
////                        - v1 * Math.cos(angle)
//                ),
//                (float) (cursor.y
//                        - v2 * Math.cos(angle)
//                        //+ v * Math.cos(angle)
//                        //- v1 * Math.sin(angle)
//                ),
//                (float) (cursor.x
//                        - v2 * Math.sin(angle)
////                        + v * Math.sin(angle)
////                        - v1 * Math.cos(angle)
//                ),
//                (float) (cursor.y
//                        + v2 * Math.cos(angle)
//                        //- v * Math.cos(angle)
//                        //- v1 * Math.sin(angle)
//                ));

//        -(2*radius*Math.tan(Math.toRadians(coneArcSize)/2))*Math.cos(angle))
//        -(2*radius*Math.tan(Math.toRadians(coneArcSize)/2))*Math.sin(angle))
//        -(2*radius*Math.tan(Math.toRadians(coneArcSize)/2))*Math.cos(angle))
//        -(2*radius*Math.tan(Math.toRadians(coneArcSize)/2))*Math.sin(angle))
    }
}
