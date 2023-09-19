package com.mygdx.game.pools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.actors.Character;
import com.mygdx.game.MainPool;
import com.mygdx.game.interfaces.CharacterSelectionListener;

public class CharactersPool extends Pool<Character> implements CharacterSelectionListener {
    private final int ID_GROUP = 100;
    private boolean selected;

    public CharactersPool(){
        Character character1= new Character(actors.size()+ID_GROUP);
        character1.setPosition(200, 100);
        character1.setSelectionListener(this);
        addActor(character1);

        Character character2= new Character(actors.size()+ID_GROUP);
        character2.setPosition(400, 100);
        character2.setSelectionListener(this);
        addActor(character2);
    }

    public void draw(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (Character character: actors) {
            character.draw(stage,shapeRenderer, mainPool);
        }

        shapeRenderer.end();
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
