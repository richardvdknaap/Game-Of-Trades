package io.gameoftrades.student44;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Terrein;

public class Node {

    private Terrein terrain;
    private Node parent;
    private double gCost = 0;
    private double hCost;
    private Coordinaat worldposistion;

    public Node(Terrein terrain, Node parent, Coordinaat end) {
        this.terrain = terrain;
        this.parent = parent;
        this.worldposistion = terrain.getCoordinaat();
        if(parent != null)
            this.gCost = parent.getgCost() + terrain.getTerreinType().getBewegingspunten();

        this.hCost = worldposistion.afstandTot(end);
    }
    public Coordinaat getWorldPosition(){
        return worldposistion;
    }

    public Node getParent() {
        return parent;
    }

    public Terrein getTerrein() {
        return terrain;
    }

    public double getgCost() {
        return this.gCost;
    }

    public double gethCost() {
        return this.hCost;
    }

    public double fCost() {
        return gethCost() + getgCost();
    }

    public void setParent(Node node){
        parent = node;
    }

    @Override
    public boolean equals(Object other) {
        if(super.equals(other))
            return true;
        if(!(other instanceof Node))
            return false;

        return this.worldposistion.equals(((Node) other).getTerrein().getCoordinaat());
    }

}