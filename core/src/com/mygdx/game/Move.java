package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Move{
    Vector2 vector;
    float action;
    public Move(Vector2 vector,float action){
        this.vector=vector;
        this.action=action;
    }

    @Override
    public String toString() {
        return vector.toString()+" "+action;
    }
}
