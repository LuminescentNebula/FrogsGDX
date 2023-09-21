package com.mygdx.game.interfaces;

import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Actionable extends Collidable, Imagable, Health {
    int getMaxAction();
    int getAction();
    void setAction(int action);
    void addAction(float action);

    int getCurrentAction();

    LinkedList<Move> getPathPoints();

    void setSelected(boolean selected);

    long getTimestamp();
}
