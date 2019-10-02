package io.gameoftrades.student44;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.*;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme {

    Node startNode;
    Node targetNode;


    @Override
    public Pad bereken(Kaart kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {

        this.startNode = new Node(true,coordinaat.getX(),coordinaat.getY());
        this.targetNode = new Node(true,coordinaat1.getX(),coordinaat1.getY());

        ArrayList<Node> openSet = new ArrayList<>();
        HashSet<Node> closedSet = new HashSet<>();
        openSet.add(startNode);

        while (openSet.size()>0) {
            Node node = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {
                if (openSet.get(i).fCost() < node.fCost() || openSet.get(i).fCost() == node.fCost()){
                    if(openSet.get(i).hCost < node.hCost){
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


            if (node.worldPosition.equals(targetNode.worldPosition)) {
                targetNode.parent = node;
                RetracePath(startNode,targetNode);
               break;
            }


            for (Node buur : getBuren(node)) {
                if (!buur.walkable || closedSet.contains(buur)) {
                    continue;
                }

                double newCostToBuur = node.gCost + node.worldPosition.afstandTot(buur.worldPosition);
                if (newCostToBuur < buur.gCost || !openSet.contains(buur)) {
                    buur.gCost = (int) newCostToBuur;
                    buur.hCost = (int) buur.worldPosition.afstandTot(targetNode.worldPosition);
                    buur.parent = node;

                    if (!openSet.contains(buur)) {
                        openSet.add(buur);
                    }
                }
            }
        }
        return null;
    }

    void RetracePath(Node startNode, Node endNode) {
        ArrayList<Node> path = new ArrayList<>();
        Node currentNode = endNode;

        while (!currentNode.worldPosition.equals(startNode.worldPosition)) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);

        for (Node cord:path){
            System.out.println(cord.worldPosition);
        }
        //System.out.println(path);
    }

    public ArrayList<Node> getBuren(Node node){
        ArrayList<Node> buren = new ArrayList<>();

        buren.add(new Node(true,node.worldPosition.naar(Richting.NOORD).getX(),node.worldPosition.naar(Richting.NOORD).getY()));
        buren.add(new Node(true,node.worldPosition.naar(Richting.OOST).getX(),node.worldPosition.naar(Richting.OOST).getY()));
        buren.add(new Node(true,node.worldPosition.naar(Richting.ZUID).getX(),node.worldPosition.naar(Richting.ZUID).getY()));
        buren.add(new Node(true,node.worldPosition.naar(Richting.WEST).getX(),node.worldPosition.naar(Richting.WEST).getY()));

        return buren;
    }
}