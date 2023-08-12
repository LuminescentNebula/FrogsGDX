package com.mygdx.game.old;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Core extends ApplicationAdapter {
    private TextureRegion textureRegion;
    private SpriteBatch spriteBatch;
    private FitViewport fitViewport;

    @Override
    public void create() {
        spriteBatch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        textureRegion = new TextureRegion(texture);
        fitViewport = new FitViewport(256, 256);
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        fitViewport.apply();
        spriteBatch.begin();
        spriteBatch.draw(textureRegion,0,0);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        fitViewport.update(width, height);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        textureRegion.getTexture().dispose();
    }
}
