package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class MoveAction extends Action{
    public Vector2 vector;
    public MoveAction(Vector2 vector, float action){
        super(action);
        this.vector=new Vector2(vector);
    }
    public MoveAction(float x, float y, float action){
        super(action);
        this.vector=new Vector2(x,y);
    }

    @Override
    public String toString() {
        return "Move{vector=" + vector + ", action=" + action + "}";
    }
}
