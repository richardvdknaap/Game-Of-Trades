package io.gameoftrades.student44;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.*;

import java.util.*;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme, Debuggable {

    private Node startNode;
    private Node targetNode;
    private Pad route;
    private Kaart kaart;
    private Debugger debugger;
    private ArrayList<Node> openSet;
    private ArrayList<Node> closedSet;

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

            Node node = getLaag();
            closedSet.add(node);

            Richting[] richtingen = node.getTerrein().getMogelijkeRichtingen();
            for(Richting richting : richtingen){
                final Node buur = new Node(kaart.kijk(node.getTerrein(),richting),node,coordinaat1);
                if(!openSet.contains(buur)&&!closedSet.contains(buur)){
                    buur.setParent(node);
                    openSet.add(buur);
                }
            }

            if (node.getWorldPosition().equals(targetNode.getWorldPosition())) {
                RetracePath(startNode,targetNode);
                break;
            }

        }
        return route;
    }

    public Node getLaag(){
        if(openSet.isEmpty()){
            return null;
        }
        Node laagste = null;
        double cost = -1;
        for(final Node node : this.openSet){
            if(cost!=-1&&node.fCost()>=cost){
                continue;
            }
            laagste = node;
            cost = node.fCost();
        }
        this.openSet.remove(laagste);
        return laagste;
    }

    public int getTijd(ArrayList<Node> nodes){
        int tijd = 0;
        for(Node node:nodes){
            //TODO Hier moeten de eerste en laatste node uitgehaald worden!!
            tijd += node.getTerrein().getTerreinType().getBewegingspunten();
        }
       return tijd;
    }


    public Richting[] getRichtingen(ArrayList<Node> coordinaten){
        ArrayList<Richting> richtingen = new ArrayList<>();
        for(int i =0; i<coordinaten.size()-2;i++){
            richtingen.add(Richting.tussen(coordinaten.get(i).getWorldPosition(),coordinaten.get(i+1).getWorldPosition()));
        }
        Richting[] richtinglijst = richtingen.toArray(new Richting[richtingen.size()]);
        return richtinglijst;
    }

    public void RetracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = endNode;
        boolean debug= true;
        while (!currentNode.getWorldPosition().equals(startNode.getWorldPosition())) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }
        path.add(startNode);
        Collections.reverse(path);

        route = new PadImpl(getRichtingen(path),getTijd(path));
        if(debug){
            this.debugger.debugPad(kaart,startNode.getWorldPosition(),route);
        }
    }
}