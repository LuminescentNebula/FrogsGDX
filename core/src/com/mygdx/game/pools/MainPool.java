package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Health;
import com.mygdx.game.interfaces.UIListener;
import com.mygdx.game.pools.CharactersPool;
import com.mygdx.game.pools.EnemyPool;
import com.mygdx.game.pools.ObstaclesPool;

import java.util.ArrayList;
import java.util.Iterator;

public class MainPool extends Pool<Pool> {
    private CharactersPool charactersPool;
    private ObstaclesPool obstaclesPool;
    private EnemyPool enemyPool;

    public MainPool(UIListener uiListener) {
        charactersPool = new CharactersPool();
        charactersPool.setCharacterUIListener(uiListener);
        obstaclesPool = new ObstaclesPool();
        enemyPool = new EnemyPool();
        addActor(charactersPool);
        //addActor(obstaclesPool);
        addActor(enemyPool);
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer,MainPool mainPool) {
        act(stage,shapeRenderer);
    }

    public void act(Stage stage, ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        //charactersPool.project(stage, shapeRenderer,this);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        charactersPool.act(stage, shapeRenderer, this);

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (Enemy enemy : enemyPool.getActors()) {

            if (enemy.getDebug()) { //Отрисовка диагонали коллизии
                shapeRenderer.rect(
                        enemy.getX(),
                        enemy.getY(),
                        enemy.getWidth(),
                        enemy.getHeight());
            }
        }
        shapeRenderer.end();
    }

    public <E> ArrayList<E> get(Class<E> clazz) {
        return new ArrayList<E>() {{
            for (Pool pool : actors) {
                addAll(pool.get(clazz));
            }
        }};
    };
}
