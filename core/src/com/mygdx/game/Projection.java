package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Movable;

public class Projection {
    public static final float MAX_LINE_LENGTH = 250f;
    private static final float LINE_THICKNESS = 15f;
    private static final int MIN_DISTANCE = 20;
    private static final int MIN_TIME_DIFFERENCE = 100;


    private static boolean calculateIntersection(Movable movable, MainPool mainPool, Vector2 cursor){
        for (Collidable other : mainPool.getCollidables()) {
            if (other.getId()!=movable.getId()) {
                Vector2 intersection = new Vector2();
                AlignmentPack alignmentPack = new AlignmentPack();

                if (AdvancedIntersector.intersectSegmentRectangle(
                        movable.getPathPoints().getLast().vector,
                        cursor,
                        other.getBounds(), intersection,alignmentPack)) {
                    intersection.x+=alignmentPack.alignmentSides.get();
                    intersection.y+=alignmentPack.alignmentLevel.get();
                    cursor.set(intersection);
                    return true;
                }
            }
        }
        return false;
    }

    public static void draw(Stage stage, Movable movable, ShapeRenderer shapeRenderer, MainPool mainPool){
        for (int i = 0; i< movable.getPathPoints().size()-1; i++){
            shapeRenderer.rectLine(
                    movable.getPathPoints().get(i).vector,
                    movable.getPathPoints().get(i+1).vector,
                    LINE_THICKNESS);
            shapeRenderer.circle(
                    movable.getPathPoints().get(i+1).vector.x,
                    movable.getPathPoints().get(i+1).vector.y,
                    LINE_THICKNESS);
        }

        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));

        Vector2 direction = cursor.cpy().sub(movable.getPathPoints().getLast().vector);
        float restriction=Math.min(MAX_LINE_LENGTH, movable.getMaxAction()- movable.getAction()- movable.getCurrentAction());
        if (direction.len() > restriction) {
            direction.setLength(restriction);
            cursor.set(movable.getPathPoints().getLast().vector.cpy().add(direction));
        }

        calculateIntersection(movable,mainPool, cursor);

        shapeRenderer.rectLine(movable.getPathPoints().getLast().vector,cursor, LINE_THICKNESS);
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

    public static void calculateProjection(Stage stage, ShapeRenderer shapeRenderer, Movable movable, MainPool mainPool){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (cursor.y<=Gdx.graphics.getHeight()-75) { //TODO: Костыль для проверки, что нажатие в верхней части, где кнопки
                //Проверка, что не слишком быстро и не слишком быстро после выделения
                if (cursor.dst(movable.getPathPoints().getFirst().vector) > MIN_DISTANCE &&
                        cursor.dst(movable.getPathPoints().getLast().vector) > MIN_DISTANCE &&
                        Math.abs(movable.getTimestamp() - TimeUtils.millis()) > MIN_TIME_DIFFERENCE) {
                    int action = checkDirection(new Vector2(movable.getProjection().getX() + movable.getWidth() / 2,
                                    movable.getProjection().getY() + movable.getHeight() / 2),
                            movable.getPathPoints().getLast().vector, movable);
                    cursor.sub(movable.getWidth() / 2, movable.getHeight() / 2);

                    if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                        movable.getPathPoints().add(new Move(
                                movable.getProjection().getX() + movable.getWidth() / 2,
                                movable.getProjection().getY() + movable.getHeight() / 2,
                                action));
                        System.out.println(movable.getPathPoints());
                        draw(stage, movable, shapeRenderer, mainPool);

                    } else {
                        movable.addAction(movable.getCurrentAction());
                        System.out.println(movable.getAction());
                        movable.setPosition(movable.getProjection().getX(), movable.getProjection().getY());
                        movable.setSelected(false);
                    }
                } else {
                    draw(stage, movable, shapeRenderer, mainPool);
                }
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            movable.addAction(movable.getCurrentAction());
            System.out.println(movable.getAction());
            movable.setPosition(
                    movable.getPathPoints().getLast().vector.x - movable.getWidth() / 2,
                    movable.getPathPoints().getLast().vector.y - movable.getHeight() / 2);
            movable.setSelected(false);
        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            if (movable.getPathPoints().size()==1) {
                movable.setSelected(false);
            } else {
                movable.addAction(-movable.getPathPoints().getLast().action);
                movable.getPathPoints().removeLast();
            }
        } else {
            draw(stage, movable, shapeRenderer, mainPool);
        }
    }

    //point1 - cursor
    //point2 - center
    private static int checkDirection(Vector2 cursor, Vector2 center, Movable character){
        Vector2 direction = cursor.cpy().sub(center);
        direction.limit(Projection.MAX_LINE_LENGTH);
        int n=0;
        if (character.getAction() +character.getCurrentAction()+ direction.len() <= character.getMaxAction()) {
             n+= direction.len();
        } else {
            direction.setLength(character.getMaxAction() - character.getAction() +-character.getCurrentAction());
            n=character.getMaxAction() - character.getAction() +-character.getCurrentAction();
        }
        character.addAction(n);
        cursor.set(center.cpy().add(direction));
        return n;
    }
}
