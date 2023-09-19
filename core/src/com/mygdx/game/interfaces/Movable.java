package com.mygdx.game.interfaces;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Movable extends Collidable {
    int getMaxAction();

    int getAction();

    void setAction(int action);

    void addAction(float action);

    int getCurrentAction();

    LinkedList<Move> getPathPoints();

    Image getImage();

    Image getSelection();

    Image getProjection();

    float getWidth();

    float getHeight();

    void setPosition(float x, float y);

    void setSelected(boolean selected);

    long getTimestamp();
}
