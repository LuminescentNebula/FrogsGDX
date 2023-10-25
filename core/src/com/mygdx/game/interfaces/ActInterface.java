package com.mygdx.game.interfaces;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.Health;

public interface ActInterface {
    boolean check(Health other, Vector2 master, Vector2 cursor, Circle circle);
}
