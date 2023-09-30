package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.Health;

public class Catapult extends BaseType {//Снаряд

    @Override
    public boolean check(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
        if (Intersector.overlaps(circle, other.getBounds())) {
            shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
            targetsSelectionListener.addTarget(other);
            return true;
        }
        return false;
    }

    @Override        //Не рисуется точками
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        float dst = Vector2.dst(master.x, master.y, cursor.x, cursor.y);
        shapeRenderer.curve(
                master.x, master.y,
                master.x, master.y + dst / 2,
                cursor.x, cursor.y + dst / 2,
                cursor.x, cursor.y,
                10);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
//                shapeRenderer.circle(master.x, master.y, 2);
//                shapeRenderer.circle(cursor.x, cursor.y, 2);
//                shapeRenderer.circle(master.x, master.y + dst / 2, 2);
//                shapeRenderer.circle(cursor.x, cursor.y + dst / 2, 2);
    }
}
