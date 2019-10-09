package io.gameoftrades.student44;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;

import java.util.*;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme, Debuggable {

    private Node startNode;
    private Node targetNode;
    private PadImpl route;
    private Kaart kaart;
    private Debugger debugger;
    private int totalCost = 0;
    private ArrayList<Node> openSet;
    private ArrayList<Node> closedSet;
    private ArrayList<Node> tiles;


    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }

    @Override
    public Pad bereken(Kaart _kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {

        this.kaart=_kaart;
        this.startNode = new Node(kaart.getTerreinOp(coordinaat),null,coordinaat1);
        this.targetNode = new Node(kaart.getTerreinOp(coordinaat1),null,coordinaat1);

        this.openSet = new ArrayList<>();
        this.closedSet = new ArrayList<>();
        openSet.add(startNode);

        while (openSet.size()>0) {
            Algoritme(true);
        }
        return route;
    }

    public synchronized void Algoritme(boolean debug){
        Node node = getLowest(true);
        closedSet.add(node);
        System.out.println(node.getWorldPosition());

        Richting[] richtingen = node.getTerrein().getMogelijkeRichtingen();

        for(Richting richting:richtingen){
            final Node buur = new Node(kaart.kijk(node.getTerrein(),richting),node,targetNode.getWorldPosition());
            if(!openSet.contains(buur) && !closedSet.contains(buur)){
                this.openSet.add(node);
            }
        }

        if (!node.getWorldPosition().equals(targetNode.getWorldPosition())) {
            return;
        }

        totalCost = (int) closedSet.get(closedSet.size() -1).getgCost();

        tiles.add(node);

        while(node.getParent()!=null){
            final Node parent = node.getParent();
            tiles.add(parent);
            node = parent;
        }

        Richting[] richtings = new Richting[tiles.size()-1];

        Collections.reverse(tiles);

        for (int x=0; x<tiles.size()-1;x++){
            richtings[x] = Richting.tussen(tiles.get(x).getWorldPosition(),tiles.get(x+1).getWorldPosition());
        }
        route = new PadImpl(richtings,totalCost);
    }


    private Node getLowest(boolean opnemen){
        if(openSet.isEmpty()){
            return null;
        }
        Node lowest =null;
        double cost = -1;

        for(final Node node : this.openSet){
            if (cost!=-1&&node.fCost()>=cost){
                continue;
            }
            lowest = node;
            cost = node.fCost();
        }

       if(opnemen){
           this.openSet.remove(lowest);
       }

        return lowest;
    }
}