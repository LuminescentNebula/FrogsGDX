package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Image background;
    private CharactersPool charactersPool;
    private Image enemy;

    public GameScreen() {
        stage = new Stage(new FitViewport(1024, 512)); //Размер viewport
        Gdx.graphics.setWindowedMode(1024,512); //Размер окна
        batch = new SpriteBatch();
        shapeRenderer =  new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        Gdx.input.setInputProcessor(stage);

        // Create images
        background = new Image(new Texture(Gdx.files.internal("background.png")));
        charactersPool = new CharactersPool();

        enemy = new Image(new Texture(Gdx.files.internal("ghost.png")));
        enemy.setSize(100,100);

        enemy.setPosition(700,100);

        // Add actors to stage
        stage.addActor(background);
        stage.addActor(charactersPool);
        stage.addActor(enemy);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update
        stage.getViewport().apply();
        batch.begin();
        stage.draw();
        charactersPool.draw(stage,shapeRenderer);

//        shapeRenderer.begin();
//        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
//        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.WHITE);
//        shapeRenderer.circle(character.getX() + character.getWidth()/2,
//                character.getY() + character.getHeight()/2,
//                250);
//        shapeRenderer.end();

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
