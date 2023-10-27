package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Collidable {
    Rectangle getBounds();

    float getWidth();
    float getHeight();
    float getCenterX();
    float getCenterY();
    Vector2 getCenter();
    void setPosition(float x, float y);
    int getId();
    //FIXME:for debug
    String getName();
}
