package com.mygdx.game.interfaces;

import com.mygdx.game.Move;

import java.util.LinkedList;

public interface Movable extends Actionable {
    LinkedList<Move> getPathPoints();

}
