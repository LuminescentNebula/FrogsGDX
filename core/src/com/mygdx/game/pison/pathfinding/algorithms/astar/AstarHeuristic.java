package com.mygdx.game.pison.pathfinding.algorithms.astar;

public interface AstarHeuristic<Node> {
    double estimate(Node node);
}
