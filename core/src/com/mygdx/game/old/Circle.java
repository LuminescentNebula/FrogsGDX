package com.mygdx.game.old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class Circle {
    public float x,y,radius,velocityX,velocityY;
    public Color color;

    public Circle(float x, float y, float radius,float velocityX,float velocityY,Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocityX=velocityX;
        this.velocityY=velocityY;
        this.color= color;
    }

    public void move(){
        x += velocityX * Gdx.graphics.getDeltaTime();
        y += velocityY * Gdx.graphics.getDeltaTime();

        if (x - radius < 10 || x + radius > Gdx.graphics.getWidth()-10) {
            velocityX = -velocityX; // Изменяем направление по x
        }
        if (y - radius < 10 || y + radius > Gdx.graphics.getHeight()-10) {
            velocityY = -velocityY; // Изменяем направление по y
        }
    }
}