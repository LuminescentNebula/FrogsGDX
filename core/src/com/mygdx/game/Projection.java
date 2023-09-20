package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Movable;

public class Projection {
    public static final float lineMaxLength = 250f;
    private static final float lineThickness = 15f;
    private static final int MIN_DISTANCE = 20;
    private static final int MIN_TIME_DIFFERENCE = 100;

    public static void draw(Stage stage, Movable movable, ShapeRenderer shapeRenderer, MainPool mainPool){
        for (int i = 0; i< movable.getPathPoints().size()-1; i++){
            shapeRenderer.rectLine(
                    movable.getPathPoints().get(i).vector,
                    movable.getPathPoints().get(i+1).vector,
                    lineThickness);
            shapeRenderer.circle(
                    movable.getPathPoints().get(i+1).vector.x,
                    movable.getPathPoints().get(i+1).vector.y,
                    lineThickness);
        }

        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));

        Vector2 direction = cursor.cpy().sub(movable.getPathPoints().getLast().vector);
        float restriction=Math.min(lineMaxLength, movable.getMaxAction()- movable.getAction()- movable.getCurrentAction());
        if (direction.len() > restriction) {
            direction.setLength(restriction);
            cursor.set(movable.getPathPoints().getLast().vector.cpy().add(direction));
        }

        //System.out.println("ID: " + movable.getId());

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for (Collidable other : mainPool) {
            //System.out.println("Other id: " + other.getId()+" "+other.getName());
            if (other.getId()!=movable.getId()) {
                Vector2 intersection = new Vector2();
                AlignmentPack alignmentPack = new AlignmentPack();
                //Теперь с пересечениями все нормально, но надо определить точку пересечния
                //Нужно передавать пересечние в следуеющую отрисовку.
                    shapeRenderer.rect(other.getBounds().getX(),
                            other.getBounds().getY(),
                            other.getBounds().getWidth(),
                            other.getBounds().getHeight());

                if (AdvancedIntersector.intersectSegmentRectangle(
                        movable.getPathPoints().getLast().vector,
                        cursor,
                        other.getBounds(), intersection,alignmentPack)) {

                    shapeRenderer.rectLine(movable.getPathPoints().get(movable.getPathPoints().size()-1).vector,
                            cursor,
                            3);

                    shapeRenderer.setColor(Color.WHITE);
                    intersection.x+=(movable.getWidth()/2)*alignmentPack.alignmentSides.get();
                    intersection.y+=(movable.getHeight()/2)*alignmentPack.alignmentLevel.get();
                    cursor = intersection;
                    shapeRenderer.circle(cursor.x, cursor.y, 5);
                    break;
                }
            }
        }

        shapeRenderer.rectLine(movable.getPathPoints().getLast().vector,cursor,lineThickness);
        transparentProjection(movable,cursor,stage);
    }

    private static void  transparentProjection(Movable movable, Vector2 cursor, Stage stage){
        movable.getProjection().setPosition(
                cursor.x - movable.getWidth()/2,
                cursor.y - movable.getHeight()/2);
        stage.getBatch().begin();
        movable.getProjection().draw(stage.getBatch(),0.5f);
        stage.getBatch().end();
    }

    public static void calculateProjection(Stage stage, ShapeRenderer shapeRenderer, Movable character, MainPool mainPool){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

            //Проверка, что не слишком быстро и не слишком быстро после выделения
            if (cursor.dst(character.getPathPoints().getFirst().vector) > MIN_DISTANCE  &&
                    cursor.dst(character.getPathPoints().getLast().vector) > MIN_DISTANCE &&
                    Math.abs(character.getTimestamp() - TimeUtils.millis())>MIN_TIME_DIFFERENCE) {
                int action=checkDirection(cursor, character.getPathPoints().getLast().vector,character);
                cursor.sub(character.getWidth() / 2, character.getHeight() / 2);

                if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                    character.getPathPoints().add(new Move(
                            character.getProjection().getX()+character.getWidth()/2,
                            character.getProjection().getY()+character.getHeight()/2,
                            action));
                    System.out.println(character.getPathPoints());
                    draw(stage, character, shapeRenderer, mainPool);

                } else {
                    character.addAction(character.getCurrentAction());
                    System.out.println(character.getAction());
                    character.setPosition(character.getProjection().getX(), character.getProjection().getY());
                    character.setSelected(false);
                }
            } else {
                draw(stage, character, shapeRenderer, mainPool);
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            character.addAction(character.getCurrentAction());
            System.out.println(character.getAction());
            character.setPosition(
                    character.getPathPoints().getLast().vector.x - character.getWidth() / 2,
                    character.getPathPoints().getLast().vector.y - character.getHeight() / 2);
            character.setSelected(false);
        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (character.getPathPoints().size()==1) {
                character.setSelected(false);
            } else {
                character.addAction(-character.getPathPoints().getLast().action);
                character.getPathPoints().removeLast();
            }
        } else {
            draw(stage, character, shapeRenderer, mainPool);
        }
    }

    //point1 - cursor
    //point2 - center
    private static int checkDirection(Vector2 point1, Vector2 point2, Movable character){
        Vector2 direction = point1.cpy().sub(point2);
        if (direction.len() > Projection.lineMaxLength) {
            direction.setLength(Projection.lineMaxLength);
        }
        int n=0;
        if (character.getAction() +character.getCurrentAction()+ direction.len() <= character.getMaxAction()) {
             n+= direction.len();
        } else {
            direction.setLength(character.getMaxAction() - character.getAction() +-character.getCurrentAction());
            n=character.getMaxAction() - character.getAction() +-character.getCurrentAction();
        }
        character.addAction(n);
        point1.set(point2.cpy().add(direction));
        return n;
    }

}
