package com.mygdx.game.interfaces;

import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Actionable extends Collidable, Imagable {
    float getMaxAction();
    float getAction();
    void setAction(float action);
    void addAction(float action);
    float getCurrentAction();

    void setSelected(boolean selected);

    long getTimestamp();
}
