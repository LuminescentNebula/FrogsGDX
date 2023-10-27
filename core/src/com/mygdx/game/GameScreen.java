package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.pools.MainPool;

public class GameScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Image background;
    private MainPool mainPool;
    private UserInterface UI;

    public GameScreen() {
        stage = new Stage(new FitViewport(1024, 512)); //Размер viewport
        Gdx.graphics.setWindowedMode(1024,512); //Размер окна
        batch = new SpriteBatch();
        shapeRenderer =  new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        Gdx.input.setInputProcessor(stage);

        UI = new UserInterface();
        UI.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        UI.build();

        mainPool = new MainPool(UI);


        //Create images
        background = new Image(new Texture(Gdx.files.internal("background.png")));
        background.setName("Background");


        // Add actors to stage
        stage.addActor(background);
        stage.addActor(mainPool);
        stage.addActor(UI);
        System.out.println(stage.getActors());
    }



    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL30.GL_BLEND);
        Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);

        // Update
        stage.getViewport().apply();
        batch.begin();
        //Отрисовка статичных объектов
        stage.draw();
        //Отрисовка действий и перемещения персонажей
        mainPool.act(stage,shapeRenderer);

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
