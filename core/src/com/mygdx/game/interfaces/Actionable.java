package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.pools.MainPool;

public interface Actionable extends ActionUpdatable{
    float getMaxAction();
    float getAvailableAction();
    void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool);
}
