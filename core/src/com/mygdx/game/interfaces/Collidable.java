package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Collidable {
    Rectangle getBounds();

    int getId();
}
