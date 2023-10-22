package com.mygdx.game.actions.types;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Draws {

    public static DrawInterface[] draws = new DrawInterface[] {
            Draws::drawShot,
            Draws::drawThroughShot,
            Draws::drawCatapult,
            Draws::drawCircle,
    };

    private static void drawCatapult(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
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
    private static void drawShot(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        clamp(master, cursor, minLength, maxLength);
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        for (int i = 0; i <= 10; i += 1) {
            shapeRenderer.circle(master.x + vec2.x * i / 10, master.y + vec2.y * i / 10, 5);
        }
    }
    private static void drawLightning(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        //TODO
        //Молния, которая бьёт по прямой и искажается каждый кадр или несколько
    }
    private static void drawThroughShot(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        clamp(master,cursor,minLength,maxLength);
        Vector2 vec2 = new Vector2(cursor).sub(new Vector2(master));
        //TODO:Костыль
        vec2.clamp((float) Math.hypot(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()), (float) Math.hypot(Gdx.graphics.getWidth(), Gdx.graphics.getWidth()));
        for (int i = 0; i <=20; i += 1) {
            shapeRenderer.circle(master.x + vec2.x*i/20, master.y + vec2.y*i/20, 5);
        }
    }
    private static void drawCircle(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(cursor.x, cursor.y, radius);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
    }
    private static void drawTarget(ShapeRenderer shapeRenderer, Vector2 master, Vector2 cursor, float minLength, float maxLength, float radius) {
        shapeRenderer.x(cursor,25);
        //TODO |
        //   -- --
        //     |
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


}
