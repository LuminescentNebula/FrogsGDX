package com.mygdx.game.pools;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public abstract class Pool<T> extends Group {
    protected ArrayList<T> actors = new ArrayList<>();
    public int getSize(){
        return actors.size();
    }

    public ArrayList<T> getActors(){
        return actors;
    }

    @Override
    public void addActor(Actor actor) {
        actors.add((T)actor);
        super.addActor(actor);
    }
}
