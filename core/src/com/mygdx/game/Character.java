package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Character extends Group {

    public boolean isSelected;
    Image character,selection;
    public Character(){
        character = new Image(new Texture(Gdx.files.internal("character.png")));
        selection = new Image(new Texture(Gdx.files.internal("selection.png")));
        selection.setVisible(false);
        addActor(character);
        addActor(selection);
        isSelected=false;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isSelected = !isSelected;
                selection.setVisible(isSelected);
                return true;
            }
        });
    }

    @Override
    public float getWidth() {
        return character.getWidth();
    }

    @Override
    public float getHeight() {
        return character.getHeight();
    }
}
