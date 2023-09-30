package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.ActDrawInterface;
import com.mygdx.game.interfaces.Health;

public class Target extends BaseType {//Снаряд

    @Override
    public boolean check(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
        if (Intersector.overlaps(circle, other.getBounds())) {
            shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
            return true;
        }
        return false;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        //   | TODO
        // -- --
        //   |
        shapeRenderer.x(cursor,25);
    }
}
