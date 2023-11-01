package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.GameScreen;
import com.mygdx.game.actions.types.mods.StopOnCollision;
import com.mygdx.game.interfaces.*;
import com.mygdx.game.pools.MainPool;
import com.mygdx.game.MoveAction;
import com.mygdx.game.Projection;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.actions.TypeFabric;
import com.mygdx.game.actions.types.Flag;
import com.mygdx.game.actions.types.Radius;
import com.mygdx.game.actions.types.*;
import com.mygdx.game.actions.types.mods.Reset;
import com.mygdx.game.actions.types.mods.Rotate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;

public class Character extends Group implements Projectable, Attackable, Collidable, Health, Selectable {

    private Image character,selection,characterProjection;
    public long timeStamp;

    protected final int maxAction=1000;     //Максимальное действие, которе можно совершить за раунд
    protected float action=0;               //Действие, которе было выполнено в текущем раунде

    ArrayList<Attack> attacks = new ArrayList<>();
    private int activeAttack=-1;

    private int health;
    private int maxHealth=100;
    private boolean targeted=false;
    @Getter
    private boolean selected=false;
    @Getter
    private boolean attacking=false;

    private boolean isCollidable=true;

    private LinkedList<MoveAction> pathPoints;
    private CharacterPoolListener poolListener;

    private Rectangle bounds = new Rectangle();
    private final int ID;

    public boolean isAttacking() {
        return attacking;
    }

    public void stopAttacking(){
        setAttacking(false,-1);
    }

    public void setAttacking(int index){
        setAttacking(true,index);
    }
    public void setAttacking(boolean attacking,int index) {
        this.attacking = attacking;
        System.out.println("Attacking "+attacking);
        attacks.forEach((attack -> {
            attack.setSelected(false);
            attack.flushTargets();
        }));
        activeAttack=index;
        if (attacking) {
            attacks.get(index).setSelected(true);
        }
    }

    public Character(int ID) {
        Texture texture = new Texture(Gdx.files.internal("character.png"));
        this.ID = ID;
        //Сам персонаж
        character = new Image(texture);
        character.setSize(50, 100);
        character.setName("Character");

        //То, что появляется при выделении
        //TODO: Зеленым при выделении и Красным при атаке
        selection = new Image(new Texture(Gdx.files.internal("selection.png")));
        selection.setSize(50, 100);
        selection.setVisible(false);
        selection.setName("Selection");

        //Проекция при движении
        characterProjection = new Image(texture);
        characterProjection.setSize(50, 100);
        characterProjection.setVisible(false);
        characterProjection.setName("Projection");

        //Добавление на сцену
        addActor(character);
        addActor(selection);
        addActor(characterProjection);

        health = 100;


        TypeFabric typeFabric = new TypeFabric()
                .setActionCost(150);

        for (int i=0;i<1;i+=1) {
            typeFabric.addType(new Type())
                    .setDraw(Draws::drawShot)
                    .addMod(new Rotate(45*i))
                    //.addMod(new Mirror(Mirror.MIRROR_X))
                    .addMod(new StopOnCollision())
                    //.addMod(new StickToTargets())
                    //.addMod(new Translate(10,10))
                    //.addMod(new TranslateRotated(10,10))
                    .addMod(new Reset(true))

                    .setAct(Acts::checkCircle)
                    .addFlag(Flag.checkNotMaster)
                    //.addFlag(Flag.stopOnFirstCollision)
                    //.addFlag(Flag.chainDamage)

                    .setLength(0, 500)
                    .setRadius(Radius.MEDIUM)
                    .setDamage(10);
        }

        attacks.add(typeFabric.build(this));

        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setSelected(!selected);
                return true;
            }
        });
    }

    @Override
    public void act(Stage stage, ShapeRenderer shapeRenderer, MainPool mainPool){
        if (isSelected()) {
            Vector2 cursor = stage.getViewport().unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
            //System.out.println(attacking);
            if (attacking) {
                attack(cursor,stage.getBatch(),shapeRenderer,mainPool);
            } else {
                move(cursor,stage.getBatch(),shapeRenderer,mainPool);
            }
        }
    }

    public void move(Vector2 cursor, Batch batch, ShapeRenderer shapeRenderer, MainPool mainPool){
        boolean result=true;
        System.out.println(action + " " +pathPoints);

        Projection.draw(cursor,batch, this, shapeRenderer, mainPool);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            if ((cursor.y <= Gdx.graphics.getHeight() - 174) && isCursorValid(cursor)) { //TODO: Костыль для проверки, что нажатие в верхней части, где кнопки
                result=Projection.calculateProjection(cursor, batch, shapeRenderer, this, mainPool,
                        Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));
            }
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            result=Projection.applyProjection(this);
        } else if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            result=Projection.cancelProjection(this);
        }
        if (!result){
            System.out.println(getAction());
            setSelected(false);
        }
    }

    public void attack(Vector2 cursor, Batch batch, ShapeRenderer shapeRenderer, MainPool mainPool) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            setSelected(!selected);
            stopAttacking();
        } else {
            attacks.get(activeAttack).check(shapeRenderer, this, cursor, mainPool);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                //attacks.get(activeAttack).deal();
                addAction(attacks.get(activeAttack).getActionCost());
                setSelected(!selected);
                stopAttacking();
            }
        }
    }

    private boolean isCursorValid(Vector2 cursor) {
        return cursor.dst(getPathPoints().getFirst().vector) > Projection.MIN_DISTANCE &&
                cursor.dst(getPathPoints().getLast().vector) > Projection.MIN_DISTANCE &&
                Math.abs(getTimestamp() - TimeUtils.millis()) > Projection.MIN_TIME_DIFFERENCE;
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
        selection.setColor(1,0,0,0.5f);
        return targeted;
    }

    @Override
    public void setSelected(boolean selected) {
        timeStamp = TimeUtils.millis();
        pathPoints = new LinkedList<>();

        pathPoints.add(new MoveAction(new Vector2(
                getX() + getWidth() / 2,
                getY() + getHeight() / 2), 0));
        this.selected = selected;
        selection.setVisible(selected);
        selection.setColor(1,1,0,0.5f);
        if (!selected) {
            attacking = false;
            attacks.forEach((attack -> attack.setSelected(false)));
        }
        try {
            poolListener.setSelected(selected);
        } catch (NullPointerException e){
            System.out.println("Selection listener is not set");
        }
    }

    public void setPoolListener(CharacterPoolListener poolListener) {
        this.poolListener = poolListener;
        //Слушатель для выделения персонажа
        removeListener(getListeners().get(0));
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!poolListener.isSelected()) {
                    setSelected(!selected);
                    System.out.println("Selected " + selected);
                    poolListener.setSelected(selected);
                    poolListener.sendAttacks(attacks);
                }
                return true;
            }
        });
    }

    @Override
    public Boolean isCollidable() {
        return isCollidable;
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
    public float getAvailableAction() {
        return Math.min((maxAction-action),poolListener.getAvailableAction());
    }

    @Override
    public float getAction() {
        return action;
    }

    @Override
    public void addAction(float action) {
        this.action+=action;
        poolListener.addAction(action);
    }

    @Override
    public LinkedList<MoveAction> getPathPoints() {
        return pathPoints;
    }

//    @Override
//    public Image getImage() {
//        return character;
//    }

//    @Override
//    public Image getSelection() {
//        return selection;
//    }

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
