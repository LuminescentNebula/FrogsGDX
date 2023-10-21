package com.mygdx.game.actions.types;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.interfaces.Health;

public interface ActDrawInterface extends ActInterface, DrawInterface {
    boolean chainCheck(Health other, ShapeRenderer shapeRenderer, Circle circle);
}
