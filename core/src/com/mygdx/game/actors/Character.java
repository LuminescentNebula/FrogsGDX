package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
import com.mygdx.game.actions.Fabric;
import com.mygdx.game.actions.Flag;
import com.mygdx.game.actions.Radius;
import com.mygdx.game.actions.types.*;
import com.mygdx.game.interfaces.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Character extends Group implements Movable, Attackable, Health {

    private Image character,selection,characterProjection;
    public long timeStamp;

    protected final int maxAction=1000;   //Максимальное действие, которе можно совершить за раунд
    protected float action=0;               //Действие, которе было выполнено в текущем раунде
    protected float currentAction;          //Действие, которое выполняется в текущем выделении персонажа

    ArrayList<Attack> attacks = new ArrayList<>();
    private int activeAttack=-1;

    private int health;
    private int maxHealth=100;
    private boolean targeted=false;
    private boolean selected=false;
    private boolean attacking=false;

    private LinkedList<Move> pathPoints;
    private CharacterSelectionListener selectionListener;

    private Rectangle bounds = new Rectangle();
    private final int ID;

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(){
        setAttacking(false,-1);
    }

    public void setAttacking(int index){
        setAttacking(true,index);
    }
    public void setAttacking(boolean attacking,int index) {
        this.attacking = attacking;
        System.out.println(attacking);
        attacks.forEach((attack -> attack.setSelected(false)));
        activeAttack=index;
        if (attacking) {
            attacks.get(index).setSelected(true);
        }
    }


    public Character(int ID){
        Texture texture = new Texture(Gdx.files.internal("character.png"));
        this.ID=ID;
        //Сам персонаж
        character = new Image(texture);
        character.setSize(50,100);
        character.setName("Character");

        //То, что появляется при выделении
        //TODO: Зеленым при выделении и Красным при атаке
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


        Fabric fabric = new Fabric()
                .setFlags((byte)(Flag.checkNotMaster));

                fabric.addType(new Type() {
                    @Override
                    public boolean check(Health other, Vector2 master, Vector2 cursor, Circle circle) {
                        return Acts.acts[1].check(other, master, cursor, circle);
                    }

                    @Override
                    public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
                        Draws.draws[2].draw(shapeRenderer,master,cursor,minLength,maxLength,radius);
                    }
                }).setLength(0,100).setRadius(Radius.LARGE).setDamage(10);





        attacks.add(fabric.build(this));

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setSelected(!selected);
                return true;
            }
        });
    }

    public void move(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool){
        if (isSelected()) {
            //System.out.println(attacking);
            if (attacking) {
                act(stage,shapeRenderer,mainPool);
            } else {
                Projection.calculateProjection(stage, shapeRenderer, this, mainPool);
            }
        }
//        if (getDebug()){ //Отрисовка диагонали коллизии
//            shapeRenderer.rectLine(getX(),getY(),getX()+getWidth(),getY()+getHeight(),10);
//        }
    }

    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            setSelected(!selected);
        } else {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            if (selected) {
                attacks.get(activeAttack).check(shapeRenderer, this, cursor, mainPool);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    //attacks.get(0).deal();
                }
            }
        }
    }

    public boolean isSelected() {
        return selected;
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
    public boolean setTargeted(boolean targeted) {
        this.targeted=targeted;
        selection.setVisible(targeted);
        return targeted;
    }

    @Override
    public void setSelected(boolean selected) {
        timeStamp = TimeUtils.millis();
        pathPoints = new LinkedList<>();

        pathPoints.add(new Move(new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2), 0));
        currentAction = 0;
        this.selected = selected;
        selection.setVisible(selected);
        if (!selected) {
            attacking = false;
            attacks.forEach((attack -> attack.setSelected(false)));
        }
        try {
            selectionListener.setSelected(selected);
        } catch (NullPointerException e){
            System.out.println("Selection listener is not set");
        }
    }

    public void setSelectionListener(CharacterSelectionListener selectionListener) {
        this.selectionListener = selectionListener;
        //Слушатель для выделения персонажа
        removeListener(getListeners().get(0));
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!selectionListener.isSelected()) {
                    setSelected(!selected);
                    System.out.println(selected);
                    selectionListener.setSelected(selected);
                    selectionListener.sendAttacks(attacks);
                }
                return true;
            }
        });
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

    @Override
    public float getMaxAction() {
        return maxAction;
    }

    @Override
    public float getAction() {
        return action;
    }

    @Override
    public void setAction(float action) {
        this.action=action;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
    }

    @Override
    public float getCurrentAction() {
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
