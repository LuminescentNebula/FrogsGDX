package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface Collidable extends Placeable {
    Rectangle getBounds();
    Boolean isCollidable();
    //FIXME:for debug
    String getName();
}
