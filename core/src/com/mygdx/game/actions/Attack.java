package com.mygdx.game.actions;

//Отрисовка траектории/области
//Выделение всех затронутых персонажей и объектов
//Трата действия персонажа


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MainPool;
import com.mygdx.game.Radius;
import com.mygdx.game.interfaces.Attackable;
import com.mygdx.game.interfaces.Health;

import java.util.ArrayList;

public class Attack {
    boolean isSelected=false;
    Attackable master;
    int damage=10;
    int area=0;
    Type type;



    public Attack(Attackable master, Type type){
        this.master = master;
        this.type = type;
    }

    private void deal(Health target){

    }

    public void draw(Stage stage,
                     ShapeRenderer shapeRenderer){
    }
    public void act(Stage stage,
                    ShapeRenderer shapeRenderer,
                    MainPool mainPool){

    }

    public interface Drawing {
        void act(Health other, ShapeRenderer shapeRenderer,Vector2 master, Vector2 cursor, Circle circle);
        void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength);
    }

    protected enum Type {
        //Нужны настройки радиуса
        //Нужно рисовать зону поражения в draw
        //Настройки урона
        //фабрика нужна
        CATAPULT(new Drawing() { //Снаряд
            @Override
            public void act(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
                if (Intersector.overlaps(circle, other.getBounds())) {
                    shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
                }
            }

            @Override        //Не рисуется точками
            public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength) {
                float dst = Vector2.dst(master.x,master.y,cursor.x,cursor.y);
                shapeRenderer.curve(
                        master.x, master.y,
                        master.x, master.y + dst / 2,
                        cursor.x, cursor.y + dst / 2,
                        cursor.x, cursor.y,
                        10);
//                shapeRenderer.circle(master.x, master.y, 2);
//                shapeRenderer.circle(cursor.x, cursor.y, 2);
//                shapeRenderer.circle(master.x, master.y + dst / 2, 2);
//                shapeRenderer.circle(cursor.x, cursor.y + dst / 2, 2);
            }
        },Radius.MEDIUM),
        TARGET(new Drawing() { //Цель
            @Override
            public void act(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
                if (Intersector.overlaps(circle, other.getBounds())) {
                    shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
                }
            }

            @Override
            public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength) {
            }
        },Radius.NONE),
        SHOT(new Drawing() { //Выстрел
            @Override
            public void act(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
                if (Intersector.intersectSegmentRectangle(master, cursor, other.getBounds())) {
                    //System.out.println(other.getId());
                    shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
                }
            }

            @Override
            public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength) {
                Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
                float length = vec2.len();
                for(int i = 0; i < length; i += 20) {
                    vec2.clamp(length - i, length - i);
                    shapeRenderer.circle(master.x + vec2.x, master.y + vec2.y, 5);
                }
            }
        },Radius.NONE,(byte)0b0011),
        CONE(new Drawing() {
            @Override
            public void act(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
//                Intersector.overlaps(rectangle, new Circle(arcX, arcY, arcRadius))
//                        || Intersector.intersectSegmentCircle(new Vector2(rectLeft, rectTop), new Vector2(rectRight, rectTop), new Circle(arcX, arcY, arcRadius))
//                        || Intersector.intersectSegmentCircle(new Vector2(rectRight, rectTop), new Vector2(rectRight, rectBottom), new Circle(arcX, arcY, arcRadius))
//                        || Intersector.intersectSegmentCircle(new Vector2(rectRight, rectBottom), new Vector2(rectLeft, rectBottom), new Circle(arcX, arcY, arcRadius))
//                        || Intersector.intersectSegmentCircle(new Vector2(rectLeft, rectBottom), new Vector2(rectLeft, rectTop), new Circle(arcX, arcY, arcRadius))
//                        || Intersector.pointInPolygon(new float[]{rectLeft, rectTop, rectRight, rectTop, rectRight, rectBottom, rectLeft, rectBottom}, 0, 8, new float[]{arcX, arcY}, 0, 2);
            }

            @Override
            public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength) {
                shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

                Gdx.gl.glEnable(GL30.GL_BLEND);
                Gdx.gl.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
                shapeRenderer.setColor(new Color(1,1,0,0.5f));
                Vector2 point1 = new Vector2( cursor);
                Vector2 point2 = new Vector2(master.x + master.dst(cursor),master.y);
                point1.sub(master).nor();
                point2.sub(master).nor();
                float angle = (MathUtils.atan2(point1.y, point1.x) - MathUtils.atan2(point2.y, point2.x));
                //System.out.println(angle*MathUtils.radiansToDegrees);

//                shapeRenderer.triangle(
//                        master.x,master.y,
//                        (float) (cursor.x-coneRadius*Math.sin(angle)-coneRadius*Math.cos(angle)),
//                        (float) (cursor.y+coneRadius*Math.cos(angle)-coneRadius*Math.sin(angle)),
//
//                        (float) (cursor.x+coneRadius*Math.sin(angle)-coneRadius*Math.cos(angle)),
//                        (float) (cursor.y-coneRadius*Math.cos(angle)-coneRadius*Math.sin(angle)));

                Vector2 direction = cursor.cpy().sub(master);
                direction.clamp(100,250);
                cursor.set(master.cpy().add(direction));

                shapeRenderer.arc(
                        master.x, master.y,
                        cursor.dst(master),
                        angle*MathUtils.radiansToDegrees- coneArcSize /2, coneArcSize);
            }
        },Radius.NONE,(byte)0b0001),
        RANGE(new Drawing() {
            @Override
            public void act(Health other, ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, Circle circle) {
                if (Intersector.overlaps(circle, other.getBounds())) {
                    shapeRenderer.circle(other.getCenterX(), other.getCenterY(), 50);
                }
            }

            @Override
            public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength) {

            }
        },Radius.MEDIUM,(byte)0b0001);


        private Drawing drawing;
        private float radius=Radius.NONE;
        private float minLength=100;
        private float maxLength=250;
        private byte flags=0;
        private final static float coneArcSize=53;
        //non static
        private static ArrayList<Health> targets = new ArrayList<>();

        public static final byte checkNotMaster = 1;  // 0001
        public static final byte stopOnFirstCollision   = 2;  // 0010


        Type(final Drawing drawing) {
            this.drawing = drawing;
        }
        Type(final Drawing drawing, float radius) {
            this.drawing = drawing;
            this.radius = radius;
        }
        Type(final Drawing drawing, float radius, byte flags) {
            this.drawing = drawing;
            this.radius = radius;
            this.flags = flags;
        }
        Type(Type type) {
            this.drawing = type.drawing;
        }


        public void draw(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float maxLength){
            drawing.draw(shapeRenderer,master,cursor,maxLength);
        }

        public void act(MainPool mainPool, Attackable master, ShapeRenderer shapeRenderer, Vector2 cursor, float radius) {
            if ((flags&checkNotMaster) == checkNotMaster) {
                for (Health other : mainPool.getHealths()) {
                    if (master.getId()!=other.getId()) {
                        drawing.act(other, shapeRenderer, master.getCenter(), cursor, new Circle(cursor,radius));
                    }
                }
            } else {
                for (Health other : mainPool.getHealths()) {
                    drawing.act(other, shapeRenderer, master.getCenter(), cursor, new Circle(cursor,radius));
                }
            }
        }
    }




    //        CONE,
    //        SINGLE_TARGET, CIRCLE
    //        SHOT,
    //        CATAPULT,
    //        CHAIN -- режим
    //        RANGE
}
