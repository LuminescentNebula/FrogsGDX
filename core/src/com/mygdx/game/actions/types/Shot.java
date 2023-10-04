package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.ActDrawInterface;
import com.mygdx.game.interfaces.Health;

public class Shot extends BaseType {//Снаряд

    @Override
    public boolean check(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
        if (Intersector.intersectSegmentRectangle(master, cursor, other.getBounds())) {
                shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 100);
                return true;
            }
        return false;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        for (int i = 0; i <=10; i += 1) {
            shapeRenderer.circle(master.x + vec2.x*i/10, master.y + vec2.y*i/10, 5);
        }

    }
}
