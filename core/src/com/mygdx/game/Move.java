package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Move{
    Vector2 vector;
    final float action;
    public Move(Vector2 vector,float action){
        this.vector=new Vector2(vector);
        this.action=action;
    }
    public Move(float x,float y,float action){
        this.vector=new Vector2(x,y);
        this.action=action;
    }

    @Override
    public String toString() {
        return "Move{vector=" + vector + ", action=" + action + "}";
    }
}
