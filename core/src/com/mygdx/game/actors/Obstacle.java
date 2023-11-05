package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.interfaces.ActionUpdatable;
import com.mygdx.game.interfaces.Collidable;

public class Obstacle extends Group implements Collidable, ActionUpdatable {

    private final int ID;
    Rectangle bounds = new Rectangle();
    Image image;
    float action;
    float updateAction;

    public Obstacle(int ID) {
        image = new Image(new Texture("Ro.png"));
        image.setSize(100,75);
        addActor(image);
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
    public float getWidth() {
        return image.getWidth();
    }

    @Override
    public float getHeight() {
        return image.getHeight();
    }


    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public Boolean isCollidable() {
        return true;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
        if (this.action>=updateAction){
            this.action-=updateAction;
            System.out.println(ID+" It is time for update");
            //do something
        }
    }

    @Override
    public float getAction() {
        return action;
    }
}
