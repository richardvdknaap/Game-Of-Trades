package io.gameoftrades.student44;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;
import io.gameoftrades.model.kaart.TerreinType;

public class Node {
    private Coordinaat worldPosition;

    private int gCost = 1;
    private int hCost;
    private Node parent;
    private Terrein terrein;

    public Node(Terrein _terrein, Node _parent, Coordinaat einde) {
        this.terrein = _terrein;
        this.parent = _parent;
        this.worldPosition = terrein.getCoordinaat();

        if(parent!=null){
            this.gCost = parent.getgCost() + terrein.getTerreinType().getBewegingspunten();
        }

        this.hCost =(int) worldPosition.afstandTot(einde);

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

    public int getgCost(){
        return gCost;
    }

    public int gethCost(){
        return hCost;
    }

    public int fCost(){
        return gCost + hCost;
    }

    public void setParent(Node _parent){
        this.parent = _parent;
    }

    public void setgCost(int _gCost){
        this.gCost = _gCost;
    }

    public void sethCost(int _hCost){
        this.hCost = _hCost;
    }
}
