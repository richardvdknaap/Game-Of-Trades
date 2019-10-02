package io.gameoftrades.student44;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme {

    double fCost;
    double hCost;
    double gCost;
    Coordinaat startNode;
    Coordinaat targetNode;
    Coordinaat current;


    @Override
    public Pad bereken(Kaart kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {

        this.startNode = coordinaat;
        this.targetNode = coordinaat1;
        //this.current = coordinaat;

        List<Coordinaat> openSet = new ArrayList<>();
        HashSet<Coordinaat> closedSet = new HashSet<>();

        openSet.add(startNode);

        while (!openSet.isEmpty()) {

            Coordinaat node = openSet.get(0);
            for (int i = 1; i < openSet.size(); i++) {

                if (openSet.get(i).fCost)

            }
            if (f_cost(c) < lowestf) {
                current = c;

            }

            open.remove(current);

            closed.add(current);

            if (current == coordinaat1) {
                return null;
            }

            Coordinaat[] buren = new Coordinaat[4];

            buren[0] = current.naar(Richting.NOORD);
            buren[1] = current.naar(Richting.OOST);
            buren[2] = current.naar(Richting.ZUID);
            buren[3] = current.naar(Richting.WEST);

            for (Coordinaat buur : buren) {
                if (closed.contains(buur)) {
                    continue;
                }
                if (!open.contains(buur)) {
                    f_cost(buur);

                    root.addKind(new Node(buur));
                    if (!open.contains(buur)) {
                        open.add(buur);
                    }
                }
            }
        }

        root.drukPreOrderAf();
        return null;

    }

    public double f_cost(Coordinaat current) {


        g_cost = Math.sqrt(Math.pow(current.getX() - c1.getX(), 2) + Math.pow(current.getY() - c1.getY(), 2));
        h_cost = Math.sqrt(Math.pow(current.getX() - c2.getX(), 2) + Math.pow(current.getY() - c2.getY(), 2));
        f_cost = h_cost + g_cost;

        return f_cost;
    }

    public void addKind(Coordinaat pad) {
        this.kinderen.add(pad);


    }
}