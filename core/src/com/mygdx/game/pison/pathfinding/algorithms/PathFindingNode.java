package com.mygdx.game.pison.pathfinding.algorithms;


import com.mygdx.game.pison.graph.Connection;

public interface PathFindingNode<Node> {
    Node getNode();

    double getCost();

    Connection<Node> getConnection();
}
