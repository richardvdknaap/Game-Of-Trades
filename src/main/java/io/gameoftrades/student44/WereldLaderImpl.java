package io.gameoftrades.student44;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.util.ArrayList;
import java.util.Scanner;

public class WereldLaderImpl implements WereldLader {

    private String resource;
    private Scanner scan;
    private String lijn;
    private String[] data;
    private int[] intData;
    private Kaart kaart;
    private int xas;
    private int yas;
    private  ArrayList<Stad> steden = new ArrayList<>();
    private ArrayList<Handel> handels = new ArrayList<>();
    private int handelHoeveelheid;
    private Markt markt;

    public void laadOmvang(){
        scan = new Scanner(this.getClass().getResourceAsStream(resource));
        lijn = scan.nextLine().trim();
        data = lijn.split(",");
        intData = new int[data.length];
        for (int i = 0; i < intData.length; i++) {
            intData[i] = Integer.parseInt(data[i]);
        }
        kaart = Kaart.metOmvang(intData[0], intData[1]);
    }

    public void laadTerrein(){
        for (int y = 0; y < intData[1]; y++) {
            for (int x = 0; x < intData[0]; x++) {
                if(intData[0]== lijn.trim().length()) {
                    char terreinChar = lijn.charAt(x);
                    TerreinType terreinType = TerreinType.fromLetter(terreinChar);
                    Coordinaat coordinaat = Coordinaat.op(x, y);
                    Terrein.op(kaart, coordinaat, terreinType);
                }
                else
                    throw new IllegalArgumentException();
            }
            lijn = scan.nextLine();
        }
    }

    public void laadSteden(){
        int stadHoeveelheid = Integer.parseInt(lijn.trim());
        lijn = scan.nextLine();
        for (int i = 0; i < stadHoeveelheid; i++) {
            lijn.trim();
            String[] stadData = lijn.split(",");
            if(coordinaatChecker(stadData)){
                throw new IllegalArgumentException();
            }
            else {
                Coordinaat coordinaat = Coordinaat.op(xas-1, yas-1);
                String stadNaam = stadData[2];
                Stad stad = Stad.op(coordinaat, stadNaam);
                steden.add(stad);
            }
            lijn = scan.nextLine();
        }
    }

    public boolean coordinaatChecker(String[] stadData){
        xas = Integer.parseInt(stadData[0]);
        yas = Integer.parseInt(stadData[1]);
        if((xas>intData[0]&&yas>intData[1])||(xas<=0&&yas<=0)){
            return true;
        }
        else
            return false;
    }

    public void laadHandel(int handelHoeveelheid){
        for (int i = 0; i < handelHoeveelheid; i++){
            for (Stad stad : steden) {
                lijn.trim();
                String[] marktData = lijn.split(",");
                
                if(marktData[0].equals(stad.getNaam())){
                    HandelType handelType = HandelType.valueOf(marktData[1]);
                    Handelswaar handelswaar = new Handelswaar(marktData[2]);
                    int prijs = Integer.parseInt(marktData[3].trim());
                    Handel handel = new Handel(stad,handelType,handelswaar,prijs);
                    handels.add(handel);
                }
            }
            lijnSkipper();
        }
    }

    public void lijnSkipper(){
        if(scan.hasNextLine()) {
            lijn = scan.nextLine();
        }
    }

    @Override
    public Wereld laad(String resource) {
        this.resource = resource;
        laadOmvang();
        lijn = scan.nextLine();
        laadTerrein();
        laadSteden();
        handelHoeveelheid = Integer.parseInt(lijn.trim());
        lijnSkipper();
        laadHandel(handelHoeveelheid);
        markt = new Markt(handels);
        return  Wereld.van(kaart, steden, markt);
    }


}
