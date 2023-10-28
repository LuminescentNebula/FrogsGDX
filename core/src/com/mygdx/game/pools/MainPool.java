package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.interfaces.Actionable;
import com.mygdx.game.interfaces.UIListener;
import java.util.ArrayList;

public class MainPool extends Pool<Pool> implements Actionable {
    private CharactersPool charactersPool;
    private ObstaclesPool obstaclesPool;
    private EnemyPool enemyPool;

    protected final int maxAction=5000;     //Максимальное действие, которе можно совершить за раунд
    protected float action=0;               //Действие, которе было выполнено в текущем раунде


    public MainPool(UIListener uiListener) {
        charactersPool = new CharactersPool();
        charactersPool.setCharacterUIListener(uiListener);
        charactersPool.setActionMaster(this);
        obstaclesPool = new ObstaclesPool();
        enemyPool = new EnemyPool();
        addActor(charactersPool);
        addActor(obstaclesPool);
        addActor(enemyPool);
    }

    @Override
    public float getMaxAction() {
        return maxAction;
    }

    @Override
    public float getAction() {
        return action;
    }

    @Override
    public float getAvailableAction() {
        return maxAction-action;
    }
    @Override
    public void addAction(float action) {
        this.action+=action;
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

        for (Pool pool : actors){
            pool.act(stage,shapeRenderer,this);
        }

//        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
//        for (Enemy enemy : enemyPool.getActors()) {
//
//            if (enemy.getDebug()) { //Отрисовка диагонали коллизии
//                shapeRenderer.rect(
//                        enemy.getX(),
//                        enemy.getY(),
//                        enemy.getWidth(),
//                        enemy.getHeight());
//            }
//        }
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
