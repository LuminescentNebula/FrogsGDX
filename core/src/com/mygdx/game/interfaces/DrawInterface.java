package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public interface DrawInterface {
    void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius);
}
