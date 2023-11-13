package com.mygdx.game.pison.pathfinding.algorithms.astar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.pison.graph.Connection;
import com.mygdx.game.pison.graph.Graph;
import com.mygdx.game.pison.graph.Path;
import com.mygdx.game.pison.graph.PathFinder;
import com.mygdx.game.pison.pathfinding.algorithms.PathFindingList;
import com.mygdx.game.pison.pathfinding.algorithms.PathFindingNode;

public class Astar implements PathFinder<Vector2> {
    AstarHeuristic<Vector2> heuristic;

    PathFindingList<Vector2> openList;
    PathFindingList<Vector2> closedList;


    public Astar(AstarHeuristic<Vector2> heuristic) {
        this.heuristic = heuristic;
    }

    public static double calculateDistance(Vector2 point1, Vector2 point2) {
        double x = Math.pow(point1.x - point2.x, 2.0);
        double y = Math.pow(point1.y - point2.y, 2.0);

        return Math.sqrt(x + y);
    }

    private void init(Vector2 start) {
        AstarNode<Vector2> startRecord = new AstarNode<>(start, null, 0, calculateDistance(start,Vector2.Zero));

        openList = new PathFindingList<>();
        openList.insert(startRecord);

        closedList = new PathFindingList<>();
    }

    @Override
    public Path<Vector2> find(Graph<Vector2> graph, Vector2 start, Vector2 end) {
        init(start);

        AstarNode<Vector2> current = null;
        while (!openList.isEmpty()) {
            current = (AstarNode<Vector2>) openList.removeSmallest();
            if (current.getNode().equals(end))
                break;

            List<Connection<Vector2>> outgoings = graph.getConnections(current.getNode());

            for (Connection<Vector2> connection : outgoings) {
                Vector2 endNode = connection.getTo();
                double endNodeCost = current.getCostSoFar() + connection.getCost();
                double endNodeHeuristic;
                if (closedList.contains(endNode)) {
                    AstarNode<Vector2> endRecord = (AstarNode<Vector2>) closedList.find(endNode);
                    // If it is not a better path just ignore
                    if (endRecord.getCostSoFar() <= endNodeCost)
                        continue;

                    closedList.remove(endNode);
                    endNodeHeuristic = endRecord.getEstimatedTotalCost() - endRecord.getCostSoFar();

                } else if (openList.contains(endNode)) {
                    AstarNode<Vector2> endRecord = (AstarNode<Vector2>) openList.find(endNode);
                    if (endRecord.getCostSoFar() <= endNodeCost)
                        continue;

                    endNodeHeuristic = endRecord.getEstimatedTotalCost() - endRecord.getCostSoFar();
                } else {
                    endNodeHeuristic = heuristic.estimate(endNode);
                }

                AstarNode<Vector2> endNodeRecord = new AstarNode<>(endNode, connection, endNodeCost,
                        endNodeCost + endNodeHeuristic);

                if (!openList.contains(endNode))
                    openList.insert(endNodeRecord);
                else openList.update(endNodeRecord);

            }

            closedList.insert(current);
        }

        if (current == null || !current.getNode().equals(end))
            return null;

        openList.printStats();
        return buildPath(current, start, closedList);
    }

    private Path<Vector2> buildPath(PathFindingNode<Vector2> current, Vector2 start, PathFindingList<Vector2> closed) {
        List<Connection<Vector2>> connections = new ArrayList<>();

        while (!current.getNode().equals(start)) {
            Connection<Vector2> connection = current.getConnection();

            connections.add(connection);
            current = closed.find(connection.getFrom());

        }

        Collections.reverse(connections);
        Path<Vector2> path = new Path<>();
        path.setEdges(connections);
        return path;
    }
}
