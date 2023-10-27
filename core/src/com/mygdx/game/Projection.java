package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Projectable;

public class Projection {
    public static final float MAX_LINE_LENGTH = 250f;
    private static final float LINE_THICKNESS = 15f;
    public static final int MIN_DISTANCE = 20;
    public static final int MIN_TIME_DIFFERENCE = 100;


    private static boolean calculateIntersection(Projectable projectable, MainPool mainPool, Vector2 cursor){
        Vector2 intersection = new Vector2();
        AlignmentPack alignmentPack = new AlignmentPack();

        for (Collidable other : mainPool.getCollidables()) {
            if (other.getId()!= projectable.getId()) {

                if (AdvancedIntersector.intersectSegmentRectangle(
                        projectable.getPathPoints().getLast().vector,
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

    public static void draw(Vector2 cursor, Batch batch, Projectable projectable, ShapeRenderer shapeRenderer, MainPool mainPool){
        for (int i = 0; i< projectable.getPathPoints().size()-1; i++){
            shapeRenderer.rectLine(
                    projectable.getPathPoints().get(i).vector,
                    projectable.getPathPoints().get(i+1).vector,
                    LINE_THICKNESS);
            shapeRenderer.circle(
                    projectable.getPathPoints().get(i+1).vector.x,
                    projectable.getPathPoints().get(i+1).vector.y,
                    LINE_THICKNESS);
        }

        Vector2 direction = cursor.cpy().sub(projectable.getPathPoints().getLast().vector);
        float restriction=Math.min(MAX_LINE_LENGTH, projectable.getMaxAction()- projectable.getAction()- projectable.getCurrentAction());
        if (direction.len() > restriction) {
            direction.setLength(restriction);
            cursor.set(projectable.getPathPoints().getLast().vector.cpy().add(direction));
        }

        calculateIntersection(projectable,mainPool, cursor);

        shapeRenderer.rectLine(projectable.getPathPoints().getLast().vector,cursor, LINE_THICKNESS);
        transparentProjection(projectable,cursor,batch);
    }

    private static void  transparentProjection(Projectable projectable, Vector2 cursor, Batch batch){
        projectable.getProjection().setPosition(
                cursor.x - projectable.getWidth()/2,
                cursor.y - projectable.getHeight()/2);
        batch.begin();
        projectable.getProjection().draw(batch,0.5f);
        batch.end();
    }

    public static boolean applyProjection(Projectable projectable){
        projectable.addAction(projectable.getCurrentAction());
        //System.out.println(projectable.getAction());
        projectable.setPosition(
                projectable.getPathPoints().getLast().vector.x - projectable.getWidth() / 2,
                projectable.getPathPoints().getLast().vector.y - projectable.getHeight() / 2);
        //projectable.setSelected(false);
        return false;
    }

    public static boolean cancelProjection(Projectable projectable){
        if (projectable.getPathPoints().size()==1) {
            //projectable.setSelected(false);
            return false;
        } else {
            projectable.addAction(-projectable.getPathPoints().getLast().action);
            projectable.getPathPoints().removeLast();
        }
        return true;
    }

    public static boolean calculateProjection(Vector2 cursor, Batch batch, ShapeRenderer shapeRenderer, Projectable projectable, MainPool mainPool, boolean shift) {
        float action = calculateAction(cursor, projectable);

        cursor.sub(projectable.getWidth() / 2, projectable.getHeight() / 2);

        if (shift) {
            projectable.getPathPoints().add(new Move(
                    projectable.getProjection().getX() + projectable.getWidth() / 2,
                    projectable.getProjection().getY() + projectable.getHeight() / 2,
                    action));
            //System.out.println(projectable.getPathPoints());
            draw(cursor, batch, projectable, shapeRenderer, mainPool);

        } else {
            projectable.addAction(projectable.getCurrentAction());
            //System.out.println(projectable.getAction());
            projectable.setPosition(projectable.getProjection().getX(), projectable.getProjection().getY());
            //projectable.setSelected(false);
            return false;
        }
        return true;
    }

    private static float calculateAction(Vector2 cursor, Projectable projectable) {
        return checkDirection(new Vector2(projectable.getProjection().getX() + projectable.getWidth() / 2,
                        projectable.getProjection().getY() + projectable.getHeight() / 2),
                projectable.getPathPoints().getLast().vector, projectable);
    }

    //point1 - cursor
    //point2 - center
    private static float checkDirection(Vector2 cursor, Vector2 center, Projectable projectable){
        Vector2 direction = cursor.cpy().sub(center);
        direction.limit(Projection.MAX_LINE_LENGTH);
        float n=0;
        if (projectable.getAction() + projectable.getCurrentAction()+ direction.len() <= projectable.getMaxAction()) {
             n+= direction.len();
        } else {
            direction.setLength(projectable.getMaxAction() - projectable.getAction() +-projectable.getCurrentAction());
            n= projectable.getMaxAction() - projectable.getAction() +-projectable.getCurrentAction();
        }
        projectable.addAction(n);
        cursor.set(center.cpy().add(direction));
        return n;
    }
}
