package com.mygdx.game.pison.visibilitygraph;

import java.util.*;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pison.graph.Connection;
import com.mygdx.game.pison.graph.Graph;
import lombok.Getter;

public class VisibilityGraph implements Graph<Vector2> {
    public Vector2 startPoint;
    public Vector2 endPoint;
    List<Polygon> polygons;
    @Getter
    List<Vector2> nodes;

    HashMap<Vector2, List<Connection<Vector2>>> connections;

    public VisibilityGraph(Vector2 start, Vector2 end, Polygon... polygons) {
        this.nodes = new ArrayList<>();

        this.startPoint = start;
        this.endPoint = end;

        this.nodes.add(start);
        this.nodes.add(end);

        this.polygons = new ArrayList<>(Arrays.asList(polygons));

        buildPolygons();

        buildConnections();
    }

    public VisibilityGraph(Vector2 start, Vector2 end) {
        this.nodes = new ArrayList<>();

        this.startPoint = start;
        this.endPoint = end;

        this.nodes.add(start);
        this.nodes.add(end);

        this.polygons=new ArrayList<>();
    }


    public void addPolygon(float[] points){
        polygons.add(new Polygon(points));
    }

    public void buildPolygons() {

        for (Polygon polygon : this.polygons) {
            float[] points = polygon.getVertices();

            for (int i = 1; i < points.length; i = i + 2) {
                this.nodes.add(new Vector2(points[i - 1], points[i]));
            }
        }
    }

    public void buildConnections() {
        this.connections = new HashMap<>();

        for (int i = 0; i < this.nodes.size(); i++) {
            List<Connection<Vector2>> connectionList = new ArrayList<>();

            for (int j = 0; j < this.nodes.size(); j++) {
                if (j != i && !intersects(this.nodes.get(i), this.nodes.get(j))) {
                    connectionList.add(new Connection<>(this.nodes.get(i), this.nodes.get(j), calculateDistance(this.nodes.get(i),this.nodes.get(j))));
                }
            }

            this.connections.put(new Vector2(this.nodes.get(i)), connectionList);
        }
    }

    public static double calculateDistance(Vector2 point1, Vector2 point2) {
        double x = Math.pow(point1.x - point2.x, 2.0);
        double y = Math.pow(point1.y - point2.y, 2.0);

        return Math.sqrt(x + y);
    }


    private boolean intersects(Vector2 startPoint, Vector2 endPoint) {
        Vector2 point1=new Vector2(startPoint);
        Vector2 point2=new Vector2(endPoint);
        for (Polygon polygon : polygons) {
            if (Intersector.intersectSegmentPolygon(point1, point2, polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(+1, 0), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(-1, 0), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, +1), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(+1, +1), point2.add(+1, +1), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, -1), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(-1, -1), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(+1, -1), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(-1, +1), point2.add(0, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(+1, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(-1, 0), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(0, +1), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(0, -1), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, -1), point2.add(0, -1), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(+1, -1), polygon)
                    && Intersector.intersectSegmentPolygon(point1.add(0, 0), point2.add(-1, +1), polygon)
            ) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Connection<Vector2>> getConnections(Vector2 from) {
        return connections.get(from);
    }
}