package com.github.alexthe666.citadel.client.render.pathfinding;

import com.github.alexthe666.citadel.server.entity.pathfinding.raycoms.MNode;

import java.util.HashSet;
import java.util.Set;

public class PathfindingDebugRenderer {
    public static Set<MNode> lastDebugNodesVisited = new HashSet<>();
    public static Set<MNode> lastDebugNodesNotVisited = new HashSet<>();
    public static Set<MNode> lastDebugNodesPath = new HashSet<>();

    public static void render(final WorldEventContext ctx) {
    }
}