package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Line {
    private static float lineMaxLength = 250f;
    private static float lineThickness = 15f;
    public static void draw(Stage stage, Character theCharacter,ShapeRenderer shapeRenderer){
        Vector2 cursor=stage.getViewport().unproject(new Vector2(Gdx.input.getX(),Gdx.input.getY()));
        Vector2 character = new Vector2((theCharacter.getX() + theCharacter.getWidth() / 2),
                (theCharacter.getY() + theCharacter.getHeight() / 2));

        Vector2 direction = cursor.cpy().sub(character);
        float length = direction.len();

        if (length > lineMaxLength) {
            direction.setLength(lineMaxLength);
            cursor.set(character.cpy().add(direction));
        }

        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rectLine(character,cursor,lineThickness);
        shapeRenderer.end();
    }
}
