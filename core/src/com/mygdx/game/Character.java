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

import java.util.LinkedList;

public class Character extends Group{

    private boolean isSelected;
    Image character,selection,characterProjection;
    public long timeStamp;

    public final int maxAction=1000;
    public int action=0;
    public int currentAction;

    public LinkedList<Move> pathPoints;
    public Vector2 center;
    private CharacterSelectionListener selectionListener;

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
                if (!selectionListener.isSelected()) {
                    setSelected(!isSelected);
                }
                return true;
            }
        });
    }

    public void draw(Stage stage,ShapeRenderer shapeRenderer){
        if (isSelected()) {
            Projection.calculateProjection(stage,shapeRenderer,this);
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        selectionListener.setSelected(selected);

        timeStamp = TimeUtils.millis();
        pathPoints = new LinkedList<>();

        center = new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2);
        pathPoints.add(new Move(center,0));
        currentAction = 0;
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

    public void setSelectionListener(CharacterSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }


}
