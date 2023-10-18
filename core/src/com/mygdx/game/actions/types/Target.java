package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.Health;

public class Target extends BaseType {//Снаряд

    @Override
    public boolean check(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
        return Intersector.overlaps(circle, other.getBounds());
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        //   | TODO
        // -- --
        //   |
        shapeRenderer.x(cursor,25);
    }
}
