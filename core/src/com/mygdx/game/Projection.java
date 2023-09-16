package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

public class Projection {
    public static final float lineMaxLength = 250f;
    private static final float lineThickness = 15f;
    private static final int MIN_DISTANCE = 20;
    private static final int MIN_TIME_DIFFERENCE = 100;

    public static void draw(Stage stage, Character theCharacter,ShapeRenderer shapeRenderer){

        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (int i=0;i<theCharacter.pathPoints.size()-1;i++){
            shapeRenderer.rectLine(
                    theCharacter.pathPoints.get(i).vector,
                    theCharacter.pathPoints.get(i+1).vector,
                    lineThickness);
            shapeRenderer.circle(
                    theCharacter.pathPoints.get(i+1).vector.x,
                    theCharacter.pathPoints.get(i+1).vector.y,
                    lineThickness);
        }

        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));

        Vector2 direction = cursor.cpy().sub(theCharacter.pathPoints.getLast().vector);
        float restriction=Math.min(lineMaxLength,theCharacter.maxAction-theCharacter.action-theCharacter.currentAction);
        if (direction.len() > restriction) {
            direction.setLength(restriction);
            cursor.set(theCharacter.pathPoints.getLast().vector.cpy().add(direction));
        }

        shapeRenderer.rectLine(theCharacter.pathPoints.getLast().vector,cursor,lineThickness);


        shapeRenderer.end();

        transparentCharacter(theCharacter,cursor,stage);
    }

    private static void  transparentCharacter(Character theCharacter,Vector2 cursor,Stage stage){
        theCharacter.characterProjection.setPosition(
                cursor.x - theCharacter.getWidth()/2,
                cursor.y - theCharacter.getHeight()/2);
        stage.getBatch().begin();
        theCharacter.characterProjection.draw(stage.getBatch(),0.5f);
        stage.getBatch().end();
    }

    public static void calculateProjection(Stage stage, ShapeRenderer shapeRenderer, Character character){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            //Проверка, что не слишком быстро и не слишком быстро после выделения
            if (cursor.dst(character.center) > MIN_DISTANCE  &&
                    cursor.dst(character.pathPoints.getLast().vector) > MIN_DISTANCE &&
                    Math.abs(character.timeStamp - TimeUtils.millis())>MIN_TIME_DIFFERENCE) {
                int action=checkDirection(cursor, character.pathPoints.getLast().vector,character);
                cursor.sub(character.getWidth() / 2, character.getHeight() / 2);

                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    character.timeStamp = TimeUtils.millis();
                    character.pathPoints.add(new Move(
                            cursor.add(
                            character.getWidth()/2,
                            character.getHeight()/2),
                            action));
                    System.out.println(character.currentAction);
                    System.out.println(character.pathPoints);
                    draw(stage, character, shapeRenderer);
                } else {
                    character.action+=character.currentAction;
                    System.out.println(character.action);
                    character.setPosition(cursor.x, cursor.y);
                    character.setSelected(false);
                }
            } else {
                draw(stage, character, shapeRenderer);
            }
        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (character.pathPoints.size()==1) {
                character.setSelected(false);
            } else {
                character.currentAction-=character.pathPoints.getLast().action;
                character.pathPoints.removeLast();
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            character.action+=character.currentAction;
            System.out.println(character.action);
            character.setPosition(
                    character.pathPoints.getLast().vector.x-character.getWidth()/2,
                    character.pathPoints.getLast().vector.y-character.getHeight()/2);
            character.setSelected(false);
        } else {
            draw(stage, character, shapeRenderer);
        }
    }

    //point1 - cursor
    //point2 - center
    private static int checkDirection(Vector2 point1,Vector2 point2,Character character){
        Vector2 direction = point1.cpy().sub(point2);
        if (direction.len() > Projection.lineMaxLength) {
            direction.setLength(Projection.lineMaxLength);
        }
        int n=0;
        if (character.action +character.currentAction+ direction.len() <= character.maxAction) {
             n+= direction.len();
        } else {
            direction.setLength(character.maxAction - character.action-character.currentAction);
            n=character.maxAction - character.action-character.currentAction;
        }
        character.currentAction+=n;
        point1.set(point2.cpy().add(direction));
        return n;
    }

}
