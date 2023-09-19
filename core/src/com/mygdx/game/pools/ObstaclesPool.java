package com.mygdx.game.pools;

import com.mygdx.game.actors.Obstacle;

public class ObstaclesPool extends Pool<Obstacle> {
    private final int ID_GROUP = 300;

    public ObstaclesPool(){
        Obstacle obstacle = new Obstacle(actors.size()+ID_GROUP);
        obstacle.setSize(50,50);
        obstacle.setPosition(100,50);
        addActor(obstacle);
    }
}
