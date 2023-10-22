package com.mygdx.game.actions.types;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainPool;

public abstract class Mods {

    public static void mirror(Vector2 vector, Vector2 origin, float angle){
        angle=(float)Math.toRadians(angle);
        float x1= (float) (origin.x+(vector.x- origin.x)*Math.cos(angle)-(vector.y-origin.y)*Math.sin(angle));
        float y1= (float) (origin.y+(vector.x- origin.x)*Math.sin(angle)-(vector.y-origin.y)*Math.cos(angle));
        vector.set(x1,y1);
    }

    public static void rotate(Vector2 vector, Vector2 origin, float angle){
        angle=(float)Math.toRadians(angle);
        float x1= (float) (origin.x+(vector.x- origin.x)*Math.cos(angle)-(vector.y-origin.y)*Math.sin(angle));
        float y1= (float) (origin.y+(vector.x- origin.x)*Math.sin(angle)+(vector.y-origin.y)*Math.cos(angle));
        vector.set(x1,y1);
    }

    /**
     * Перемещает vector на указанные относительные координаты.
     *
     * @param  vector      the vector to be translated
     * @param  translation the translation vector
     */
    public static void translate(Vector2 vector, Vector2 translation){
        vector.set(vector.add(translation));
    }
    /**
     * Перемещает vector относительно origin на translation
     * @see Mods#translateRotated(Vector2, Vector2, float, float)
     */
    public static void translateRotated(Vector2 vector,Vector2 origin,Vector2 translation){
        translateRotated(vector,origin,translation.x,translation.y);
    }
    /**
     * Перемещает vector относительно origin на translation
     *<a href="https://www.geogebra.org/m/enryxfpm">Link</a>
     * {@link Mods#translateRotated(Vector2, Vector2, Vector2)}
     *
     * @param  vector       the Vector2 object to be translated
     * @param  origin       the origin point relative to which the translation is performed
     * @param  translationX Указывает перемещение по касательной
     * @param  translationY Указывает перемещение от центра и к центру
     */
    public static void translateRotated(Vector2 vector,Vector2 origin,float translationX,float translationY){
        float angle = (float) Math.atan2(vector.y - origin.y, vector.x - origin.x);
        vector.set((float) (vector.x-translationX*Math.sin(angle)+translationY*Math.sin(angle)),
                (float) (vector.y+translationX*Math.cos(angle)+translationY*Math.sin(angle)));
    }

    public static void stopOnCollision(MainPool mainPool){
        //TODO
    }

    public static void stickToTargets(MainPool mainPool){
        //TODO
    }

    /**
     * "EVERYONEEEEEE!"
     * @param mainPool
     */
    public static void attackEVERYONE(MainPool mainPool){
        //TODO
    }
    public static void attackEVERYONEenemies(MainPool mainPool){
        //TODO
    }
    public static void attackEVERYONEfriends(MainPool mainPool){
        //TODO
    }
}

