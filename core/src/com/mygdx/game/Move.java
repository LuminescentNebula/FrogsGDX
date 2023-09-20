package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Move{
    Vector2 vector;
    float action;
    public Move(Vector2 vector,float action){
        this.vector=vector;
        this.action=action;
    }
    public Move(float x,float y,float action){
        this.vector=new Vector2(x,y);
        this.action=action;
    }

    @Override
    public String toString() {
        return vector.toString()+" "+action;
    }
}
