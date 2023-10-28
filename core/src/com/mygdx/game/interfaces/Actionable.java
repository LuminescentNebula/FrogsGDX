package com.mygdx.game.interfaces;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.pools.MainPool;

public interface Actionable {
    float getMaxAction();
    float getAvailableAction();
    float getAction();
    void addAction(float action);
    void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool);
}
