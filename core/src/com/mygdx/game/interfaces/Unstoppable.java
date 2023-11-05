package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Vector2;
import java.util.LinkedList;

public interface Unstoppable extends Actionable{
    LinkedList<Vector2> getTrajectory();
}
