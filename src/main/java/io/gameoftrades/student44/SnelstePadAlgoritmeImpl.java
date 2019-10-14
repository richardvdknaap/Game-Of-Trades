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

    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }

    @Override
    public Pad bereken(Kaart _kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {

        this.kaart=_kaart;
        this.startNode = new Node(kaart.getTerreinOp(coordinaat),null,coordinaat1);
        this.targetNode = new Node(kaart.getTerreinOp(coordinaat1),null,coordinaat1);

        ArrayList<Node> openSet = new ArrayList<>();
        ArrayList<Node> closedSet = new ArrayList<>();
        openSet.add(startNode);


        while (openSet.size()>0) {
            Node node = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {
                if (openSet.get(i).fCost() < node.fCost() || openSet.get(i).fCost() == node.fCost()){
                    if(openSet.get(i).gethCost() < node.gethCost()){
                        node = openSet.get(i);
                    }
                }
            }

            Iterator<Node> iter = openSet.iterator();
            while (iter.hasNext()){
                if(iter.next().equals(node)){
                   iter.remove();
                }
            }
            closedSet.add(node);


            if (node.getWorldPosition().equals(targetNode.getWorldPosition())) {
                targetNode.setParent(node);
                RetracePath(startNode,targetNode);
                break;
            }



            for (Node buur : getBuren(node,closedSet,openSet)) {
                double newCostToBuur = buur.getWorldPosition().afstandTot(startNode.getWorldPosition()) + buur.getTerrein().getTerreinType().getBewegingspunten();
                if(!buur.getTerrein().getTerreinType().isToegankelijk() || closedSet.contains(buur)){
                    continue;
                }

                if (node.getWorldPosition().afstandTot(targetNode.getWorldPosition()) < buur.getWorldPosition().afstandTot(targetNode.getWorldPosition()) || !openSet.contains(buur)) {
                    buur.setgCost((int)newCostToBuur);
                    buur.sethCost((int) buur.getWorldPosition().afstandTot(targetNode.getWorldPosition()));
                    buur.setParent(node);

                    if (!openSet.contains(buur)) {
                        openSet.add(buur);
                    }
                }
            }
        }
        return route;
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

    public ArrayList<Node> getBuren(Node node, ArrayList<Node> closed, ArrayList<Node> open){
        ArrayList<Node> buren = new ArrayList<>();
        Richting[] richtingen = node.getTerrein().getMogelijkeRichtingen();

        System.out.println(closed.size());

        for(Richting richting:richtingen){
            final Node buur = new Node(kaart.kijk(node.getTerrein(),richting),node,targetNode.getWorldPosition());

            if(!open.contains(buur) && !closed.contains(buur)) {
                buren.add(new Node(kaart.kijk(node.getTerrein(), richting), node, targetNode.getWorldPosition()));
            }


        }

        return buren;
    }
}