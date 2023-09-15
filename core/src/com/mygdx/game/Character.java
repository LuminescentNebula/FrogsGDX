package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Character extends Group {
    private boolean isSelected;
    Image character,selection,characterProjection;
    public long timeStamp;

    public final int maxAction=1000;
    public int action=0;
    public int currentAction;

    public LinkedList<Vector2> pathPoints;

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
            drawProjection(stage,shapeRenderer);
        }
    }

    public void drawProjection(Stage stage, ShapeRenderer shapeRenderer){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            //Проверка, что не слишком быстро и не слишком быстро после выделения
            if (cursor.dst(center) > 20  && cursor.dst(pathPoints.getLast()) > 20 && Math.abs(timeStamp - TimeUtils.millis())>100) {
                checkDirection(cursor, pathPoints.getLast());
                cursor.sub(getWidth() / 2, getHeight() / 2);

                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    timeStamp = TimeUtils.millis();
                    pathPoints.add(cursor.add(getWidth()/2,getHeight()/2));
                    System.out.println(currentAction);
                    System.out.println(pathPoints);
                    Projection.draw(stage, this, shapeRenderer);
                } else {
                    action+=currentAction;
                    System.out.println(action);
                    setPosition(cursor.x, cursor.y);
                    setSelected(false);
                }
            } else {
                Projection.draw(stage, this, shapeRenderer);
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            setSelected(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            action+=currentAction;
            System.out.println(action);
            setPosition(pathPoints.getLast().x-getWidth()/2,pathPoints.getLast().y-getHeight()/2);
            setSelected(false);
        } else {
            Projection.draw(stage, this, shapeRenderer);
        }
    }

    //point1 - cursor
    //point2 - center
    private void checkDirection(Vector2 point1,Vector2 point2){
        Vector2 direction = point1.cpy().sub(point2);
        if (direction.len() > Projection.lineMaxLength) {
            direction.setLength(Projection.lineMaxLength);
        }
        if (action +currentAction+ direction.len() <= maxAction) {
            currentAction += direction.len();
        } else {
            direction.setLength(maxAction - action-currentAction);
            currentAction=maxAction - action;
        }
        point1.set(point2.cpy().add(direction));
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
        pathPoints.add(center);
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
