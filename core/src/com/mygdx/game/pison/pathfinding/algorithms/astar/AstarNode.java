package com.mygdx.game.pison.pathfinding.algorithms.astar;

import com.mygdx.game.pison.graph.Connection;
import com.mygdx.game.pison.pathfinding.algorithms.PathFindingNode;
import lombok.Getter;

@Getter
public class AstarNode<Node> implements PathFindingNode<Node> {
    Node node;
    Connection<Node> connection;
    double costSoFar;
    double estimatedTotalCost;

    public AstarNode(Node node, Connection<Node> connection, double costSoFar, double estimatedTotalCost) {
        this.node = node;
        this.connection = connection;
        this.costSoFar = costSoFar;
        this.estimatedTotalCost = estimatedTotalCost;
    }

    public double getCost() {
        return estimatedTotalCost;
    }

}
