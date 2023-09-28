package com.mygdx.game.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainPool;
import com.mygdx.game.Radius;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

public class Catapult extends Attack {
    public Catapult(Attackable master){
        super(master, Type.CONE);
    }

    @Override
    public void draw(Stage stage, ShapeRenderer shapeRenderer) {
        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        type.draw(shapeRenderer,master.getCenter(), cursor, Radius.MEDIUM);
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool){
        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        type.act(mainPool,master,shapeRenderer,cursor, Radius.NONE);
    }

    private void drawTargetPoint(Health other, ShapeRenderer shapeRenderer, Vector2 cursor){
        //Цель - Точка
            if (other.getBounds().contains(cursor)){
                shapeRenderer.circle(other.getCenterX(),other.getCenterY(),50);
            }
    }
}
