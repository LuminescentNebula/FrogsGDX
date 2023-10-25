package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.interfaces.Health;

public class Enemy extends Group implements Health {

    Rectangle bounds = new Rectangle();
    Image image;
    private int ID;

    private int health;
    private int maxHealth=100;
    private boolean targeted=false;

    public Enemy(int ID){
        image = new Image(new Texture(Gdx.files.internal("ghost.png")));
        addActor(image);
        this.ID = ID;
    }

    @Override
    public void setPosition(float x, float y) {
        bounds.set(x, y, getWidth(), getHeight());
        super.setPosition(x, y);
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

    @Override
    public void setHealth(int health) {
        this.health=health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void dealHealth(int health) {
        this.health-=health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        this.maxHealth=maxHealth;
    }

    @Override
    public boolean setTargeted(boolean targeted) {
        this.targeted=targeted;
        return targeted;
    }
}
