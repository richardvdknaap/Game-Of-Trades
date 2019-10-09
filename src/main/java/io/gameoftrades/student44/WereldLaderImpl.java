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
    private  ArrayList<Stad> steden;
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
        lijn = scan.nextLine();
        for (int y = 0; y < intData[1]; y++) {
            for (int x = 0; x < intData[0]; x++) {
                if(intData[0]== lijn.trim().length()) {
                    char terreinChar = lijn.charAt(x);
                    TerreinType terreinType = TerreinType.fromLetter(terreinChar);
                    Coordinaat coordinaat = Coordinaat.op(x, y);
                    Terrein.op(kaart, coordinaat, terreinType);
                }
                else{
                    throw new IllegalArgumentException();
                }
            }
            lijn = scan.nextLine();
        }
    }

    public void laadSteden(){
        steden = new ArrayList<>();
        int stadHoeveelheid = Integer.parseInt(lijn.trim());
        lijn = scan.nextLine();
        for (int x = 0; x < stadHoeveelheid; x++) {
            lijn.trim();
            String[] stadData = lijn.split(",");
            int xas = Integer.parseInt(stadData[0]);
            int yas = Integer.parseInt(stadData[1]);
            if((xas>intData[0]&&yas>intData[1])||(xas<=0&&yas<=0)){
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

    public void laadHandel(){
        int handelHoeveelheid = Integer.parseInt(lijn.trim());
        if(scan.hasNextLine()) {
            lijn = scan.nextLine();
        }
        ArrayList<Handel> handels = new ArrayList<>();
        for (int x = 0; x < handelHoeveelheid; x++){
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
            if(scan.hasNextLine()){
                lijn = scan.nextLine();
            }
        }
        markt = new Markt(handels);
    }
    @Override
    public Wereld laad(String resource) {
        this.resource = resource;
        laadOmvang();
        laadTerrein();
        laadSteden();
        laadHandel();
        return  Wereld.van(kaart, steden, markt);
    }


}
