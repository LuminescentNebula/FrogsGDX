package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Enemy;

public class EnemyPool extends Pool<Enemy> {
    private final int ID_GROUP = 200;

    public EnemyPool() {
        Enemy enemy = new Enemy(actors.size() + ID_GROUP);
        enemy.setSize(75,75);
        enemy.setPosition(700,100);
        addActor(enemy);
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        for (Enemy enemy: actors) {
            enemy.act(stage,shapeRenderer,mainPool);
        }
    }

    @Override
    public void update(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool, float action) {

    }
}
