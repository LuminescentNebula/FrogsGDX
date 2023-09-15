package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Projection {
    public static float lineMaxLength = 250f;
    private static float lineThickness = 15f;
    public static void draw(Stage stage, Character theCharacter,ShapeRenderer shapeRenderer){

        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);

        for (int i=0;i<theCharacter.pathPoints.size()-1;i++){
            shapeRenderer.rectLine(
                    theCharacter.pathPoints.get(i),
                    theCharacter.pathPoints.get(i+1),
                    lineThickness);
        }

        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));

        Vector2 direction = cursor.cpy().sub(theCharacter.pathPoints.getLast());
        float restriction=Math.min(lineMaxLength,theCharacter.maxAction-theCharacter.action);
        if (direction.len() > restriction) {
            direction.setLength(restriction);
            cursor.set(theCharacter.pathPoints.getLast().cpy().add(direction));
        }

        shapeRenderer.rectLine(theCharacter.pathPoints.getLast(),cursor,lineThickness);


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
}
