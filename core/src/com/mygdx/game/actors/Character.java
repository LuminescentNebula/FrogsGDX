package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.MainPool;
import com.mygdx.game.Move;
import com.mygdx.game.Projection;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.actions.Catapult;
import com.mygdx.game.interfaces.*;

import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedList;

public class Character extends Group implements Movable, Attackable, Health {

    private boolean isSelected=false;
    private Image character,selection,characterProjection;
    public long timeStamp;

    protected final int maxAction=1000;   //Максимальное действие, которе можно совершить за раунд
    protected int action=0;               //Действие, которе было выполнено в текущем раунде
    protected int currentAction;          //Действие, которое выполняется в текущем выделении персонажа

    ArrayList<Attack> attacks =  new ArrayList<>();

    private int health;
    private int maxHealth=100;

    private LinkedList<Move> pathPoints;
    private CharacterSelectionListener selectionListener;

    private Rectangle bounds = new Rectangle();
    private final int ID;

    public Character(int ID){
        Texture texture = new Texture(Gdx.files.internal("character.png"));
        this.ID=ID;
        //Сам персонаж
        character = new Image(texture);
        character.setSize(50,100);
        character.setName("Character");

        //То, что появляется при выделении
        selection = new Image(new Texture(Gdx.files.internal("selection.png")));
        selection.setSize(50,100);
        selection.setVisible(false);
        selection.setName("Selection");

        //Проекция при движении
        characterProjection = new Image(texture);
        characterProjection.setSize(50,100);
        characterProjection.setVisible(false);
        characterProjection.setName("Projection");

        //Добавление на сцену
        addActor(character);
        addActor(selection);
        addActor(characterProjection);

        health=100;

        attacks.add(new Catapult(this));

        //Слушатель для выделения персонажа
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!selectionListener.isSelected()) {
                    setSelected(!isSelected);
                }
                return true;
            }
        });
    }

    public void move(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool){
        if (isSelected()) {
            Projection.calculateProjection(stage,shapeRenderer,this, mainPool);
        }
//        if (getDebug()){ //Отрисовка диагонали коллизии
//            shapeRenderer.rectLine(getX(),getY(),getX()+getWidth(),getY()+getHeight(),10);
//        }
    }

    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        //shapeRenderer.set(ShapeRenderer.ShapeType.Point);
        if (isSelected()) {
            attacks.get(0).draw(stage, shapeRenderer);
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                attacks.get(0).act(stage, shapeRenderer, mainPool);
            }
        }
    }

    public boolean isSelected() {
        return isSelected;
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
    public void setSelected(boolean selected) {
        selectionListener.setSelected(selected);

        timeStamp = TimeUtils.millis();
        pathPoints = new LinkedList<>();

        pathPoints.add(new Move(new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2),0));
        currentAction = 0;
        isSelected = selected;
        selection.setVisible(selected);
    }

    @Override
    public long getTimestamp() {
        return timeStamp;
    }

    @Override
    public float getWidth() {
        return character.getWidth();
    }

    @Override
    public float getHeight() {
        return character.getHeight();
    }

    public void setSelectionListener(CharacterSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
    }

    @Override
    public int getMaxAction() {
        return maxAction;
    }

    @Override
    public int getAction() {
        return action;
    }

    @Override
    public void setAction(int action) {
        this.action=action;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
    }

    @Override
    public int getCurrentAction() {
        return currentAction;
    }

    @Override
    public LinkedList<Move> getPathPoints() {
        return pathPoints;
    }

    @Override
    public Image getImage() {
        return character;
    }

    @Override
    public Image getSelection() {
        return selection;
    }

    @Override
    public Image getProjection() {
        return characterProjection;
    }

    @Override
    public void setPosition(float x, float y) {
        bounds.set(x, y, characterProjection.getWidth(), characterProjection.getHeight());
        toFront();
        super.setPosition(x, y);
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
    public ArrayList<Attack> getAttacks(){
        return attacks;
    }
}
