package com.mygdx.game.pools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.actors.Enemy;

public class EnemyPool extends Pool<Enemy> {
    private Enemy enemy;
    private final int ID_GROUP = 200;

    public EnemyPool() {
        enemy = new Enemy(actors.size() + ID_GROUP);
        enemy.setSize(100,100);
        enemy.setPosition(700,100);
        addActor(enemy);
    }
}
