package io.gameoftrades.student44;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.*;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.HandelType;
import io.gameoftrades.model.markt.Handelswaar;
import io.gameoftrades.model.markt.Markt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class WereldLaderImpl implements WereldLader {

    @Override
    public Wereld laad(String resource) {

        Scanner s = new Scanner(this.getClass().getResourceAsStream(resource));


        //FIRST PART KAART GROOTTE
        String line = s.nextLine().trim();
        String[] data = line.split(",");
        int[] intData = new int[data.length];

        for (int i = 0; i < intData.length; i++) {
            intData[i] = Integer.parseInt(data[i]);
        }
        Kaart k = Kaart.metOmvang(intData[0], intData[1]);


        //SECOND PART TERREIN
        line = s.nextLine();
        for (int y = 0; y < intData[1]; y++) {
            for (int x = 0; x < intData[0]; x++) {
                if(intData[0]==line.trim().length()) {
                    char t2 = line.charAt(x);
                    TerreinType tt = TerreinType.fromLetter(t2);
                    Coordinaat c = Coordinaat.op(x, y);
                    Terrein.op(k, c, tt);
                }
                else{
                    throw new IllegalArgumentException();
                }
            }
            line = s.nextLine();
        }

        //THIRD PART STEDEN
        ArrayList<Stad> a = new ArrayList<>();
        int cityAmount = Integer.parseInt(line.trim());
        line = s.nextLine();

        for (int x = 0; x < cityAmount; x++) {
            line.trim();
            String[] citydata = line.split(",");
            int xas = Integer.parseInt(citydata[0]);
            int yas = Integer.parseInt(citydata[1]);
            if((xas>intData[0]&&yas>intData[1])||(xas<=0&&yas<=0)){
                throw new IllegalArgumentException();
            }
            else {
                Coordinaat coordinaat = Coordinaat.op(xas, yas);
                String naam = citydata[2];
                Stad stad = Stad.op(coordinaat, naam);
                a.add(stad);
            }

            line = s.nextLine();

        }

        //FOURTH PART HANDEL
        int handelAmount = Integer.parseInt(line.trim());
        if(s.hasNextLine()) {
            line = s.nextLine();
        }
        ArrayList<Handel> handels = new ArrayList<>();
        for (int x = 0; x < handelAmount; x++){
            for (Stad stad : a) {
                line.trim();
                String[] marktData = line.split(",");

                /*System.out.println(a);
                System.out.println(marktData[0]);
                System.out.println(!a.contains(marktData));
                if(a.stream(marktData).anyMatch("s"::equals)){
                    throw new IllegalArgumentException();
                }*/


                if(marktData[0].equals(stad.getNaam())){
                    HandelType handelType = HandelType.valueOf(marktData[1]);
                    Handelswaar handelswaar = new Handelswaar(marktData[2]);
                    int prijs = Integer.parseInt(marktData[3].trim());
                    Handel h = new Handel(stad,handelType,handelswaar,prijs);
                    handels.add(h);
                }
            }
        }
        Markt m = new Markt(handels);

        return  Wereld.van(k,a,m);
    }


}
