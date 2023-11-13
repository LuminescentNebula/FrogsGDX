package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.interfaces.Collidable;
import com.mygdx.game.pison.graph.Connection;
import com.mygdx.game.pison.visibilitygraph.VisibilityGraph;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.MoveAction;
import com.mygdx.game.interfaces.Health;
import com.mygdx.game.interfaces.Movable;

import java.util.*;

public class Enemy extends Group implements Movable, Collidable,Health {

    Rectangle bounds = new Rectangle();
    Image image;
    private final int ID;

    private int health;
    private int maxHealth=100;
    private boolean targeted=false;

    protected final int maxAction=1000;
    protected float action=0;
    private LinkedList<MoveAction> pathPoints;
    //TODO: Any Angle pathfinding
    //todo: Draw projection when character making multi point moving
    public Enemy(int ID){
        image = new Image(new Texture(Gdx.files.internal("ghost.png")));
        addActor(image);
        this.ID = ID;
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        //buildGraph(mainPool.get(Collidable.class),shapeRenderer, mainPool.get(Character.class).get(0).getCenter(), mainPool.get(Character.class).get(0).getId());
        //move();
    }



    private float[] points(Rectangle rectangle) {
        return new float[] {
            rectangle.x, rectangle.y,
            rectangle.x + rectangle.width, rectangle.y,
            rectangle.x + rectangle.width, rectangle.y + rectangle.height,
            rectangle.x, rectangle.y + rectangle.height
        };
    }

    //Добовляем размеры объекта к каждому препятсвию, чобы убедиться, что он влазит в путь
    private float[] points(Rectangle rectangle,Rectangle actor) {
        return new float[] {
                rectangle.x-actor.width/2, rectangle.y-actor.height/2,
                rectangle.x + rectangle.width+actor.width/2, rectangle.y-actor.height/2,
                rectangle.x + rectangle.width+actor.width/2, rectangle.y + rectangle.height+actor.height/2,
                rectangle.x-actor.width/2, rectangle.y + rectangle.height+actor.height/2
        };
    }

    public void buildGraph(ArrayList<Collidable> collidables,ShapeRenderer shapeRenderer, Vector2 end,int targetID) {
        VisibilityGraph visibilityGraph= new VisibilityGraph(getCenter(),end);
        for (Collidable collidable : collidables) {
            if (collidable.getId() != getId() && collidable.getId() != targetID) {
                visibilityGraph.addPolygon(points(collidable.getBounds(),this.getBounds()));
            }
        }
        visibilityGraph.buildPolygons();
        visibilityGraph.buildConnections();

        shapeRenderer.setColor(0,0,1,0.2f);
        for (Vector2 key:visibilityGraph.getNodes()) {
            List<Connection<Vector2>> connections = visibilityGraph.getConnections(key);
            for (int i = 0; i < connections.size(); i++) {
                shapeRenderer.rectLine(connections.get(i).getFrom(), connections.get(i).getTo(), 2);
            }
        }
    }

    public void move(Vector2 cursor, Batch batch, ShapeRenderer shapeRenderer, MainPool mainPool){
        //Projection.calculateProjection();
    }

    @Override
    public void setSize(float width,float height){
        image.setSize(width,height);
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
    public Boolean isCollidable() {
        return true;
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

    @Override
    public float getMaxAction() {
        return maxAction;
    }

    @Override
    public float getAvailableAction() {
        //TODO
        return 0;
    }

    @Override
    public float getAction() {
        return action;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
    }

    @Override
    public LinkedList<MoveAction> getPathPoints() {
        return pathPoints;
    }
}
