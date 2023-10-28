package com.mygdx.game.interfaces;

import com.mygdx.game.MoveAction;

import java.util.LinkedList;

public interface Movable extends Actionable,Placeable {
    LinkedList<MoveAction> getPathPoints();

}
