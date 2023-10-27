package com.mygdx.game.actions.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actions.Attack;
import com.mygdx.game.interfaces.DrawInterface;

public abstract class Draws {

    public static DrawInterface[] draws = new DrawInterface[] {
            Draws::drawShot,
            Draws::drawThroughShot,
            Draws::drawCatapult,
            Draws::drawCircle,
            Draws::drawRange,
            Draws::drawTarget,
            Draws::drawNone
    };
    public static void drawNone(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius){

    }

    public static void drawCatapult(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1,1,1, 1);
        clamp(master,cursor,minLength,maxLength);
        float dst = Vector2.dst(master.x, master.y, cursor.x, cursor.y);
        curve(
                master.x, master.y,
                master.x, master.y + dst / 2,
                cursor.x, cursor.y + dst / 2,
                cursor.x, cursor.y,
                10,shapeRenderer);
        drawCircle(shapeRenderer, master, cursor, minLength, maxLength, radius);
    }
    public static void drawShot(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        clamp(master, cursor, minLength, maxLength);
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        for (int i = 0; i <= 10; i += 1) {
            shapeRenderer.circle(master.x + vec2.x * i / 10, master.y + vec2.y * i / 10, 5);
        }
    }

//    public static void drawLightning(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
//        //TODO
//        //Молния, которая бьёт по прямой и искажается каждый кадр или несколько
//    }

    public static void drawThroughShot(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        clamp(master,cursor,minLength,maxLength);
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        //TODO:Костыль
        vec2.clamp((float) Math.hypot(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()), (float) Math.hypot(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()));
        for (int i = 0; i <=20; i += 1) {
            shapeRenderer.circle(master.x + vec2.x*i/20, master.y + vec2.y*i/20, 5);
        }
    }

    public static void drawRange(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius){
        drawCircle(shapeRenderer, master, master, minLength, maxLength, radius);
    }
    public static void drawCircle(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(cursor.x, cursor.y, radius);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
    }
    public static void drawTarget(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.x(cursor,25);
        //TODO |
        //   -- --
        //     |
    }

    public static void drawCone(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float coneArcSize){
        float angle = getAngle(master, cursor);

        Vector2 direction = cursor.cpy().sub(master);
        direction.clamp(minLength, maxLength);

        cursor.set(master.cpy().add(direction));

        shapeRenderer.setColor(Attack.areaColor);
        shapeRenderer.arc(
                master.x, master.y,
                cursor.dst(master),
                angle * MathUtils.radiansToDegrees - coneArcSize / 2, coneArcSize);

    }

    private static void clamp(Vector2 master, Vector2 cursor, float minLength, float maxLength) {
        cursor.sub(master).clamp(minLength,maxLength).add(master);
    }
    private static void curve(float x1, float y1, float cx1, float cy1, float cx2, float cy2, float x2, float y2, int segments,ShapeRenderer shapeRenderer) {
        float subdiv_step = 1f / segments;
        float subdiv_step2 = subdiv_step * subdiv_step;
        float subdiv_step3 = subdiv_step * subdiv_step * subdiv_step;

        float pre1 = 3 * subdiv_step;
        float pre2 = 3 * subdiv_step2;
        float pre4 = 6 * subdiv_step2;
        float pre5 = 6 * subdiv_step3;

        float tmp1x = x1 - cx1 * 2 + cx2;
        float tmp1y = y1 - cy1 * 2 + cy2;

        float tmp2x = (cx1 - cx2) * 3 - x1 + x2;
        float tmp2y = (cy1 - cy2) * 3 - y1 + y2;

        float fx = x1;
        float fy = y1;

        float dfx = (cx1 - x1) * pre1 + tmp1x * pre2 + tmp2x * subdiv_step3;
        float dfy = (cy1 - y1) * pre1 + tmp1y * pre2 + tmp2y * subdiv_step3;

        float ddfx = tmp1x * pre4 + tmp2x * pre5;
        float ddfy = tmp1y * pre4 + tmp2y * pre5;

        float dddfx = tmp2x * pre5;
        float dddfy = tmp2y * pre5;

        while (segments-- > 0) {
            shapeRenderer.circle(fx, fy, 5);
            fx += dfx;
            fy += dfy;
            dfx += ddfx;
            dfy += ddfy;
            ddfx += dddfx;
            ddfy += dddfy;
        }
        shapeRenderer.circle(fx, fy, 5);
    }

    static float getAngle(Vector2 master, Vector2 cursor) {
        Vector2 point1 = new Vector2(cursor).sub(master).nor();
        Vector2 point2 = new Vector2(master.x + master.dst(cursor), master.y).sub(master).nor();
        float angle = (MathUtils.atan2(point1.y, point1.x) - MathUtils.atan2(point2.y, point2.x));
        return angle;
    }

}
