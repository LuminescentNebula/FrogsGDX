package com.mygdx.game.pison;

import com.badlogic.gdx.math.Polygon;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pison.visibilitygraph.VisibilityGraph;

public class VisibilityGraphDemo  {

    public static void main(String[] args) {
        VisibilityGraph vg = buildDemoGraph();
    }

    private static VisibilityGraph buildDemoGraph() {
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        Polygon p5 = new Polygon();
        Polygon p6 = new Polygon();
        Polygon p7 = new Polygon();

        p1.setVertices(new float[]{70, 30, 20, 60, 80, 90});

        p2.setVertices(new float[]{540, 310, 520, 350, 490, 350, 460, 310,480, 270,520, 270});

        p3.setVertices(new float[]{220, 170,180, 180,230, 300,270, 290});

        p4.setVertices(new float[]{290, 330,220, 330,235, 360,270, 360});

        p5.setVertices(new float[]{60, 120,160, 200,90, 90});

        p6.setVertices(new float[]{300, 150,480, 200,400, 300});

        p7.setVertices(new float[]{400, 160,460, 40,300, 20,300, 120});

        Vector2 start = new Vector2(100, 360);
        Vector2 end = new Vector2(560, 50);

        return new VisibilityGraph(start, end, p1, p2, p3, p4, p5, p6, p7);
    }
}
