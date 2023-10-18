package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Health;
import com.mygdx.game.interfaces.UIListener;
import com.mygdx.game.pools.CharactersPool;
import com.mygdx.game.pools.EnemyPool;
import com.mygdx.game.pools.ObstaclesPool;

import java.util.ArrayList;
import java.util.Iterator;

public class MainPool extends Group {
    private CharactersPool charactersPool;
    private ObstaclesPool obstaclesPool;
    private EnemyPool enemyPool;

    MainPool(UIListener uiListener) {
        charactersPool = new CharactersPool();
        charactersPool.setCharacterUIListener(uiListener);
        obstaclesPool = new ObstaclesPool();
        enemyPool = new EnemyPool();
        addActor(charactersPool);
        //addActor(obstaclesPool);
        addActor(enemyPool);
    }

    void act(Stage stage, ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        //charactersPool.project(stage, shapeRenderer,this);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        charactersPool.act(stage,shapeRenderer,this);

        shapeRenderer.end();
    }

    public ArrayList<Collidable> getCollidables() {
        return new ArrayList<Collidable>() {{
            addAll(charactersPool.getActors());
            //addAll(obstaclesPool.getActors());
            addAll(enemyPool.getActors());
        }};
    }

    public ArrayList<Health> getHealths() {
        return new ArrayList<Health>() {{
            addAll(charactersPool.getActors());
            //addAll(enemyPool.getActors());
        }};
    }


}
