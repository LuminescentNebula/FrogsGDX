package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Obstacle;

public class ObstaclesPool extends Pool<Obstacle> {
    private final int ID_GROUP = 300;
    Obstacle obstacle;
    public ObstaclesPool(){
        obstacle = new Obstacle(actors.size()+ID_GROUP);
        obstacle.setPosition(500,500);
        addActor(obstacle);
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {

        //obstacle.getCenter();
    }

    @Override
    public void update(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool, float action) {

    }
}
