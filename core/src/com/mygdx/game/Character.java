package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;

public class Character extends Group {
    private boolean isSelected;
    Image character,selection,characterProjection;

    public long startTime;
    public Character(){
        character = new Image(new Texture(Gdx.files.internal("character.png")));
        selection = new Image(new Texture(Gdx.files.internal("selection.png")));
        character.setSize(50,100);
        selection.setSize(50,100);
        selection.setVisible(false);
        characterProjection = new Image(new Texture(Gdx.files.internal("character.png")));
        characterProjection.setSize(50,100);
        characterProjection.setVisible(false);

        addActor(character);
        addActor(selection);
        addActor(characterProjection);

        isSelected=false;

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setSelected(!isSelected);
                return true;
            }
        });
    }

    public void drawProjection(Stage stage, ShapeRenderer shapeRenderer){
        if (Gdx.input.justTouched()) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            Vector2 ch = new Vector2(
                    getX() + getWidth() / 2,
                    getY() + getHeight() / 2);

            if (cursor.dst(ch) > 20 && Math.abs(startTime-TimeUtils.millis())>100) {
                Vector2 direction = cursor.cpy().sub(ch);
                if (direction.len() > Projection.lineMaxLength) {
                    direction.setLength(Projection.lineMaxLength);
                    cursor.set(ch.cpy().add(direction));
                }
                setSelected(false);
                setPosition(
                        cursor.x - getWidth() / 2,
                        cursor.y - getHeight() / 2);
            } else {
                Projection.draw(stage, this, shapeRenderer);
            }
        } else {
            Projection.draw(stage, this, shapeRenderer);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        startTime = TimeUtils.millis();

        isSelected = selected;
        selection.setVisible(selected);
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
