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
        if (targetsSelectionListener.isStopOnFirstCollision()) {
            if (Intersector.intersectSegmentRectangle(master, cursor, other.getBounds())) {
                //System.out.println(other.getId());
                shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
                return true;
            }
        } else {

        }
        return false;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        float length = vec2.len();
        for (int i = 0; i < length; i += 20) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.circle(master.x + vec2.x, master.y + vec2.y, 5);
        }
    }
}
