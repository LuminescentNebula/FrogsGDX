package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CharactersPool extends Group implements CharacterSelectionListener {

    private Character character1,character2;

    private boolean selected;

    CharactersPool(){
        character1= new Character();
        character2= new Character();
        // Set character position and listeners
        character1.setPosition(200, 100);
        character2.setPosition(400, 100);
        character1.setSelectionListener(this);
        character2.setSelectionListener(this);
        addActor(character1);
        addActor(character2);
    }

    public void draw(Stage stage, ShapeRenderer shapeRenderer) {
        character1.draw(stage,shapeRenderer);
        character2.draw(stage,shapeRenderer);
    }


    @Override
    public void setSelected(boolean selected) {
        this.selected=selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }
}
