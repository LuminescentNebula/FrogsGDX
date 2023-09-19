package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.pools.CharactersPool;
import com.mygdx.game.pools.EnemyPool;
import com.mygdx.game.pools.ObstaclesPool;

import java.util.ArrayList;
import java.util.Iterator;

public class MainPool extends Group implements Iterable<Collidable> {
    private CharactersPool charactersPool;
    private ObstaclesPool obstaclesPool;
    private EnemyPool enemyPool;

    MainPool() {
        charactersPool = new CharactersPool();
        obstaclesPool = new ObstaclesPool();
        enemyPool = new EnemyPool();
        addActor(charactersPool);
        addActor(obstaclesPool);
        addActor(enemyPool);
    }

    void draw(Stage stage, ShapeRenderer shapeRenderer) {
        charactersPool.draw(stage, shapeRenderer,this);
        //obstaclesPool.draw(stage,shapeRenderer);
    }


    @Override
    public Iterator<Collidable> iterator() {
        return new Iterator<Collidable>() {
            private int currentIndex = 0;
            private int size = charactersPool.getSize() + obstaclesPool.getSize() + enemyPool.getSize();

            private ArrayList<Collidable> elements = new ArrayList() {{
                addAll(charactersPool.getActors());
                addAll(obstaclesPool.getActors());
                addAll(enemyPool.getActors());
            }};

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public Collidable next() {
                return elements.get(currentIndex++);
            }

            @Override
            public void remove() {

            }
        };
    }
}
