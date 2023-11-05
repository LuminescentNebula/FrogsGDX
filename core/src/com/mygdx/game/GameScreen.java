package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
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

    public static final int worldWidth=1920;
    public static final int worldHeight=1080;

    public GameScreen() {
        stage = new Stage(new FitViewport(worldWidth, worldHeight)); //Размер viewport

        Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setUndecorated(false);
        Gdx.graphics.setResizable(true);
        Gdx.graphics.setFullscreenMode(displayMode);
        Gdx.graphics.setWindowedMode(worldWidth/2, worldHeight/2);

        batch = new SpriteBatch();
        shapeRenderer =  new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
        Gdx.input.setInputProcessor(stage);

        UI = new UserInterface();
        UI.setBounds(0,stage.getHeight()-150, stage.getWidth(), stage.getHeight());
        UI.build();

        mainPool = new MainPool(UI);

        //Create images
        background = new Image(new Texture(Gdx.files.internal("CmBkMag.png")));
        background.setName("Background");
        background.setFillParent(true);

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
