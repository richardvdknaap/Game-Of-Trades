package io.gameoftrades.student44;

import io.gameoftrades.model.kaart.Coordinaat;

public class Node {
    public Boolean walkable;
    public Coordinaat worldPosition;
    public int gridX;
    public int gridY;

    public int gCost;
    public int hCost;
    public Node parent;

    public Node(Boolean _walkable, int _gridX, int _gridY) {
        walkable = _walkable;
        gridX = _gridX;
        gridY = _gridY;
        worldPosition = Coordinaat.op(gridX,gridY);
    }

    public int fCost(){
        return gCost + hCost;
    }
}
