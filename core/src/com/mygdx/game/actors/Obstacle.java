package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.interfaces.ActionUpdatable;
import com.mygdx.game.interfaces.Collidable;

public class Obstacle extends Image implements Collidable {

    private int ID;
    Rectangle bounds = new Rectangle();
    public Obstacle(int ID) {
        super(new Texture("badlogic.jpg"));
        setSize(50,50);
        this.ID = ID;
    }

    @Override
    public void setPosition(float x, float y) {
        bounds.set(x, y, getWidth(), getHeight());
        super.setPosition(x, y);
    }

    @Override
    public float getCenterX(){
        return getX()+getWidth()/2;
    }
    @Override
    public float getCenterY(){
        return getY()+getHeight()/2;
    }

    @Override
    public Vector2 getCenter(){
        return new Vector2(getCenterX(), getCenterY());
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public int getId() {
        return ID;
    }

}
