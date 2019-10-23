package io.gameoftrades.student44;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeMath.random;

public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {
    private Debugger debugger;
    private Kaart kaart;
    private List<Stad> steden;
    private int totalSteden;
    private int popSize;
    private ArrayList<Integer> order;
    private int[] population;
    private ArrayList<Double> fitness;
    private int recordDistance;
    private ArrayList<Integer> bestEver;
    private ArrayList<Stad> bestRoute;
    private Stad currentBest;
    private Handelaar handelaar;
    private SnelstePadAlgoritme algoritme;


    @Override
    public List<Stad> bereken(Kaart _kaart, List<Stad> _list) {
        this.kaart = _kaart;
        this.steden = _list;
        this.totalSteden = steden.size();
        this.popSize = 500;
        this.currentBest = steden.get(0);
        this.handelaar = new HandelaarImpl();
        this.algoritme = handelaar.nieuwSnelstePadAlgoritme();

        this.order = new ArrayList<>();
        this.bestEver = new ArrayList<>();
        this.bestRoute = new ArrayList<>();
        setUp();
        for(int a:bestEver){
            System.out.print(a + " ");
        }
        System.out.println(recordDistance);

        for(int x=0;x<200;x++) {
            int d = calcDistance(steden, order);
            if (d < recordDistance) {
                recordDistance = d;
                bestEver = order;
            }
            nextOrder();
        }
        for (int k = 0; k < bestEver.size(); k++) {
            int n = bestEver.get(k);
            bestRoute.add(steden.get(n));
        }
        System.out.println(" ");
        for(int a:bestEver){
            System.out.print(a + " ");
        }
        System.out.println(recordDistance);
        debugger.debugSteden(kaart, bestRoute);
        return bestRoute;


    }

    public void setUp(){

        for(int i=0; i<steden.size();i++){
            order.add(i);
        }

        int d = calcDistance(steden,order);
        recordDistance = d;
        bestEver = order;
    }

    public ArrayList<Integer> swapOrder(ArrayList<Integer> a, int i, int j){
        int temp = a.get(i);
        a.set(i,a.get(j));
        a.set(j,temp);
        return a;
    }


    public int calcDistance(List<Stad> steden, List<Integer> order){
        int sum = 0;
        for (int i=0; i<order.size()-1;i++){
            Stad stadA = steden.get(order.get(i));
            Stad stadB = steden.get(order.get(i+1));
            Coordinaat van = stadA.getCoordinaat();
            Coordinaat naar = stadB.getCoordinaat();
            int d = algoritme.bereken(kaart,van,naar).getTotaleTijd();
            sum += d;
        }
        return sum;
    }

    public void nextOrder(){
        int largestI = -1;
        for(int i = 0; i<order.size() -1 ; i++){
            if(order.get(i)<order.get(i+1)){
                largestI = i;
            }
        }
        if(largestI ==-1){
            return;
        }
        int largestJ = -1;
        for(int j = 0; j<order.size(); j++){
            if(order.get(largestI)<order.get(j)){
                largestJ = j;
            }
        }
        //order = swapOrder(order,largestI,largestJ);
        order = swapOrder(order,largestI,largestJ);

        ArrayList<Integer> tail = new ArrayList<>(order.subList(largestI+1, order.size()));
        ArrayList<Integer> head = new ArrayList<>(order.subList(0, largestI+1));
        Collections.reverse(tail);
        order.clear();
        order.addAll(head);
        order.addAll(tail);

    }


    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }
}
