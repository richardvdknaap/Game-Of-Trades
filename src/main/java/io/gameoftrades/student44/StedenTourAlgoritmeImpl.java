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

import java.sql.SQLOutput;
import java.util.*;

import static java.lang.Math.pow;
import static jdk.nashorn.internal.objects.NativeMath.floor;
import static jdk.nashorn.internal.objects.NativeMath.random;

public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {
    private Debugger debugger;
    private Kaart kaart;
    private List<Stad> steden;
    private int totalSteden;
    private int popSize;
    private ArrayList<Integer> order;
    private ArrayList<ArrayList<Integer>> population;
    private ArrayList<Double> fitness;
    private int recordDistance = Integer.MAX_VALUE;
    private ArrayList<Integer> bestEver;
    private ArrayList<Stad> bestRoute;
    private ArrayList<Stad> debuggsteden;
    private ArrayList<Integer> currentBest;
    private Handelaar handelaar;
    private SnelstePadAlgoritme algoritme;
    private ArrayList<ArrayList<Integer>> newPopulation;


    @Override
    public List<Stad> bereken(Kaart _kaart, List<Stad> _list) {
        this.kaart = _kaart;
        this.steden = _list;
        this.totalSteden = steden.size();
        this.popSize = 9000;
        this.handelaar = new HandelaarImpl();
        this.algoritme = handelaar.nieuwSnelstePadAlgoritme();

        this.order = new ArrayList<>();
        this.bestEver = new ArrayList<>();
        this.bestRoute = new ArrayList<>();
        this.debuggsteden = new ArrayList<>();
        this.population = new ArrayList<>();
        this.fitness = new ArrayList<>();

        setUp();

        for(int x=0;x<20;x++) {
            System.out.println("x = " + x);
            calculateFitness();
            normalizeFitness();
            nextGeneration();
            debuggsteden.clear();
            for (int k = 0; k < bestEver.size(); k++) {
                int n = bestEver.get(k);
                debuggsteden.add(steden.get(n));
            }
            debugger.debugSteden(kaart, debuggsteden);
        }
        for (int k = 0; k < bestEver.size(); k++) {
            int n = bestEver.get(k);
            bestRoute.add(steden.get(n));
        }
        debugger.debugSteden(kaart, bestRoute);
        return bestRoute;


    }

    public void setUp(){

        for(int i=0; i<steden.size();i++){
            order.add(i);
        }

        for(int i=0; i<popSize;i++){
            ArrayList<Integer> newOrder = new ArrayList<>(shuffle(order,100));
            population.add(newOrder);
        }
    }
    public void calculateFitness() {
        int currentRecord = Integer.MAX_VALUE;
        for (int i = 0; i < population.size(); i++) {
            int d = calcDistance(steden, population.get(i));
            if (d < recordDistance) {
                recordDistance = d;
                System.out.println(recordDistance);
                bestEver = population.get(i);
            }
            if (d < currentRecord) {
                currentRecord = d;
                currentBest = population.get(i);
            }

            double math = 1 / (pow(d, 8) + 1);
            fitness.add(math);

        }
    }

    public void normalizeFitness(){
        float sum = 0;
        for(int i = 0; i <fitness.size(); i++){
            sum += fitness.get(i);
        }
        for(int i = 0; i <fitness.size(); i++){
            fitness.set(i,fitness.get(i)/sum);
        }
    }

    public void nextGeneration(){
        newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            ArrayList<Integer> orderA = pickOne(population, fitness);
            ArrayList<Integer> orderB = pickOne(population, fitness);
            ArrayList<Integer> order = crossOver(orderA, orderB);
            mutate(order);
            newPopulation.add(order);
        }
        population = newPopulation;
    }

    /**
     * Ik weet niet zeker of ik die goed heb geimplementeerd. De meeste getallen
     * die er uit komen zijn nu tussen de 0.14 en 0.16...
     * Ik heb het nu wel werkend gekregen... maar ik weet niet precies of het de
     * juiste implementatie geeft. Ik zie het wel bij filmpje 5.
     */
    public ArrayList<Integer> pickOne(ArrayList<ArrayList<Integer>> poplist, ArrayList<Double> fitnessprob){
        int index = 0;
        double r =random(1);
        while(r > 0){
            r = r - fitnessprob.get(index);
            index ++;
        }
        index--;
        return poplist.get(index);
    }

    public ArrayList<Integer> crossOver (ArrayList<Integer> orderA, ArrayList<Integer> orderB){
        int start = new Random().nextInt(orderA.size());
        int end = new Random().nextInt((orderA.size()+1)-start) + start;
        ArrayList<Integer> neworder = new ArrayList<>(order.subList(start, end));
        for(int i=0;i<orderB.size();i++){
            int city = orderB.get(i);
            if(!neworder.contains(city)){
                neworder.add(city);
            }
        }
        return neworder;
    }


    public void mutate(ArrayList<Integer> order){
        int indexA = new Random().nextInt(order.size());
        int indexB = new Random().nextInt(order.size());
        swapOrder(order, indexA, indexB);
    }


    public ArrayList<Integer> shuffle(ArrayList<Integer> a, int n){
        ArrayList<Integer> newarray = new ArrayList<>();
        for(int i = 0; i<n; i++){
            int indexA = new Random().nextInt(a.size());
            int indexB = new Random().nextInt(a.size());
            newarray = swapOrder(a,indexA,indexB);
        }
        return newarray;
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


    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }
}
