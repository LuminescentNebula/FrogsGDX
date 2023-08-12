package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Enemy extends Actor {

    Image image;
    public Enemy(){
        image = new Image(new Texture(Gdx.files.internal("ghost.png")));

    }
}
