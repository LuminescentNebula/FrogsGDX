package com.mygdx.game.actions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.Health;

public interface ActDrawInterface {
    boolean check(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle);
    void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius);
}
