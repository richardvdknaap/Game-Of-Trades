package io.gameoftrades.student44;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;

public class Node {
    private Coordinaat worldPosition;

    private double gCost;
    private double hCost;
    private Node parent;
    private Terrein terrein;

    public Node(Terrein _terrein,  Node _parent, Coordinaat einde) {
        this.terrein = _terrein;
        this.parent = _parent;
        this.worldPosition = terrein.getCoordinaat();

        if(parent!=null){
            this.gCost = parent.getgCost() + terrein.getTerreinType().getBewegingspunten();
        }

        this.hCost = worldPosition.afstandTot(einde);

    }

    public Node getParent (){
        return parent;
    }

    public Coordinaat getWorldPosition(){
        return worldPosition;
    }

    public Terrein getTerrein(){
        return terrein;
    }

    public double getgCost(){
        return gCost;
    }

    public double gethCost(){
        return hCost;
    }

    public double fCost(){
        return gCost + hCost;
    }

    public void setParent(Node _parent){
        this.parent = _parent;
    }

    public void setgCost(double _gCost){
        this.gCost = _gCost;
    }

    public void sethCost(double _hCost){
        this.hCost = _hCost;
    }
}
