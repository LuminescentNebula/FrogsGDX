package com.mygdx.game.actions.mods;

import com.badlogic.gdx.math.Vector2;

public class Reset{
    boolean reset;
    Vector2 checkpoint=new Vector2();

    public Reset(boolean reset) {
        this.reset=reset;
    }


    public void save(Vector2 cursor) {
        checkpoint.set(cursor);
    }
    public void revert(Vector2 cursor) {
        if (reset) {
            cursor.set(checkpoint);
        }
    }

}
