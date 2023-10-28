package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Vector2;

public interface Placeable {
    int getId();
    float getWidth();
    float getHeight();
    float getCenterX();
    float getCenterY();
    Vector2 getCenter();
    void setPosition(float x, float y);
}
