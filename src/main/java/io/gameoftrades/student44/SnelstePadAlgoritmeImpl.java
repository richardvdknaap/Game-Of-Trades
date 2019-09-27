package io.gameoftrades.student44;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

import java.util.ArrayList;
import java.util.List;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme {

   private List<Coordinaat> kinderen;

    double f_cost;
    double h_cost;
    double g_cost;
    Coordinaat c1;
    Coordinaat c2;
    Coordinaat current;

    static class Node{

        private List<Node> kinderen;
        private Coordinaat waarde;

        public Node(Coordinaat waarde) {
            this.waarde = waarde;
            kinderen = new ArrayList<>();
        }

        public void addKind(Node kind){
            this.kinderen.add(kind);
        }
        public void drukPreOrderAf(){
            System.out.println(waarde);
            for(Node kind:kinderen){
                kind.drukPreOrderAf();
            }

        }
    }
    @Override
    public Pad bereken(Kaart kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {

        this.c1 = coordinaat;
        this.c2 = coordinaat1;
        this.current = coordinaat;
        Node root = new Node(this.current);

        ArrayList<Coordinaat> open = new ArrayList<>();
        ArrayList<Coordinaat> closed = new ArrayList<>();

        open.add(coordinaat);
        System.out.println(open.size());
        for(Coordinaat c: open) {

            double lowestf = 999;

            if(f_cost(c) < lowestf){
                current = c;

            }

            open.remove(current);

            closed.add(current);

            if(current == coordinaat1){
                return null;
            }

            Coordinaat[] buren = new Coordinaat[4];

            buren[0] = current.naar(Richting.NOORD);
            buren[1] = current.naar(Richting.OOST);
            buren[2] = current.naar(Richting.ZUID);
            buren[3] = current.naar(Richting.WEST);

          for(Coordinaat buur: buren)
          {
              if (closed.contains(buur) ){
                  continue;
              }
              if (!open.contains(buur)){
                  f_cost(buur);

                  root.addKind(new Node(buur));
                if (!open.contains(buur)){
                    open.add(buur);
                }
              }
          }
        }

            root.drukPreOrderAf();
           return null;

    }

    public double f_cost(Coordinaat current){


        g_cost = Math.sqrt(Math.pow(current.getX()-c1.getX(),2)+Math.pow(current.getY()- c1.getY(),2));
        h_cost = Math.sqrt(Math.pow(current.getX()-c2.getX(),2)+Math.pow(current.getY()- c2.getY(),2));
        f_cost = h_cost + g_cost;

        return f_cost;
    }
    public void addKind(Coordinaat pad) {
        this.kinderen.add(pad);


    }

}
