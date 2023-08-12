package com.mygdx.game.old;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class MyGame extends ApplicationAdapter {
    private FitViewport fitViewport;
    private SpriteBatch spriteBatch;
    private Sprite sprite;
    private TextureRegion textureRegion;
    private ArrayList<Circle> circles; // Добавьте это поле в ваш класс
    private float squareX, squareY;
    private final float SQUARE_SIZE = 25;
    private final int CIRCLES_NUMBER = 10;
    private final float BORDER_SIZE = 50;
    private int cursorRotation=0;


    @Override
    public void create() {
        spriteBatch = new SpriteBatch();

        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        textureRegion = new TextureRegion(texture);
        fitViewport = new FitViewport(256, 256);

        //cursor
        sprite = new Sprite(new Texture("point.png"));
        sprite.setSize(SQUARE_SIZE, SQUARE_SIZE);
        float centerX = sprite.getWidth() / 2;
        float centerY = sprite.getHeight() / 2;
        sprite.setOrigin(centerX, centerY);
        squareX = Gdx.graphics.getWidth() / 2 - SQUARE_SIZE / 2;
        squareY = Gdx.graphics.getHeight() / 2 - SQUARE_SIZE / 2;
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

    }

    private boolean isInsideCircle(float x, float y, Circle circle) {
        float distanceSquared = (x - circle.x) * (x - circle.x) + (y - circle.y) * (y - circle.y);
        return distanceSquared <= circle.radius * circle.radius;
    }

    private void generateCircles(int CIRCLES_NUMBER){
        circles = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < CIRCLES_NUMBER; i++) { // Генерируем CIRCLES_NUMBER кружочков
            float x = random.nextFloat() * (Gdx.graphics.getWidth() - 2 * BORDER_SIZE) + BORDER_SIZE;
            float y = random.nextFloat() * (Gdx.graphics.getHeight() - 2 * BORDER_SIZE) + BORDER_SIZE;
            float radius = 20 + random.nextFloat() * 30; // случайный радиус от 20 до 50
            float velocityX = 50 + random.nextFloat() * 100; // начальная скорость по x
            float velocityY = 50 + random.nextFloat() * 100; // начальная скорость по y
            Color color = new Color(random.nextFloat(),random.nextFloat(),random.nextFloat(),1);
            circles.add(new Circle(x, y, radius,velocityX,velocityY,color));
        }
    }

    private void renderCircles(ShapeRenderer shapeRenderer){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Circle circle : circles) {
            //System.out.println(circle.velocityX +" "+ circle.velocityY+" "+circle.color);
            shapeRenderer.setColor(circle.color);
            shapeRenderer.circle(circle.x, circle.y, circle.radius);

            circle.move();
        }
        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            for (Circle circle : circles) {
                if (isInsideCircle(touchX, touchY, circle)) {
                    //circle.color.set(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
                    circles.remove(circle);
                    break;
                }
            }
        }
        shapeRenderer.end();
    }

    public void renderCursor(){
        squareX = Gdx.input.getX() - SQUARE_SIZE / 2;
        squareY = Gdx.graphics.getHeight() - Gdx.input.getY() - SQUARE_SIZE / 2;
        cursorRotation=(cursorRotation+1)%360;
        sprite.setRotation(cursorRotation);

        spriteBatch.begin();
        sprite.setPosition(squareX, squareY);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }
}

