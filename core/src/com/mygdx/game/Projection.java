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
        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        Vector2 character = new Vector2((theCharacter.getX() + theCharacter.getWidth() / 2),
                (theCharacter.getY() + theCharacter.getHeight() / 2));

        Vector2 direction = cursor.cpy().sub(character);
        if (direction.len() > lineMaxLength) {
            direction.setLength(lineMaxLength);
            cursor.set(character.cpy().add(direction));
        }

        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rectLine(character,cursor,lineThickness);
        shapeRenderer.end();

        theCharacter.characterProjection.setPosition(
                cursor.x - theCharacter.getWidth()/2,
                cursor.y - theCharacter.getHeight()/2);
        stage.getBatch().begin();
        theCharacter.characterProjection.draw(stage.getBatch(),0.5f);
        stage.getBatch().end();

    }
}
