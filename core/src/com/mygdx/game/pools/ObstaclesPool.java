package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Obstacle;

public class ObstaclesPool extends Pool<Obstacle> {
    private final int ID_GROUP = 300;

    public ObstaclesPool(){
        Obstacle obstacle = new Obstacle(actors.size()+ID_GROUP);
        obstacle.setSize(50,50);
        obstacle.setPosition(100,50);
        addActor(obstacle);
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {

    }
}
