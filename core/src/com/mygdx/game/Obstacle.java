package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Obstacle extends Image {
    Obstacle(){
        super(new Texture("badlogic.jpg"));
        setSize(50,50);
    }
}
