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

    @Override
    public Wereld laad(String resource){
        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //


        Scanner s = new Scanner(this.getClass().getResourceAsStream(resource));


        //FIRST PART
        String line = s.nextLine().trim();
        String[] data = line.split(",");
        int[] intData = new int[data.length];

        for (int i = 0; i < intData.length; i++) {
            intData[i] = Integer.parseInt(data[i]);
        }
        Kaart k = Kaart.metOmvang(intData[0], intData[1]);


        //SECOND PART
        line = s.nextLine();
        for (int y = 0; y < intData[1]; y++) {
            for (int x = 0; x < intData[0]; x++) {
                char t2 = line.charAt(x);
                TerreinType tt = TerreinType.fromLetter(t2);
                Coordinaat c = Coordinaat.op(x, y);
                Terrein.op(k, c, tt);
            }
            line = s.nextLine();
        }

        //THIRD PART
        ArrayList<Stad> a = new ArrayList<>();
        int cityAmount = Integer.parseInt(line);
        line = s.nextLine();

        for (int x = 0; x < cityAmount; x++) {
            line.trim();
            String[] citydata = line.split(",");
            int[] cityint = new int[citydata.length];

            int xas = Integer.parseInt(citydata[0]);
            int yas = Integer.parseInt(citydata[1]);
            Coordinaat coordinaat = Coordinaat.op(xas, yas);
            String naam = citydata[2];
            Stad stad = Stad.op(coordinaat, naam);
            a.add(stad);


            line = s.nextLine();

        }

        //FOURTH PART
        int handelAmount = Integer.parseInt(line);
        System.out.println(handelAmount);
        line = s.nextLine();
        ArrayList<Handel> handels = new ArrayList<>();
        for (int x = 0; x < handelAmount; x++){
            for (Stad stad : a) {
                line.trim();
                String[] marktData = line.split(",");
                if(marktData[0].equals(stad.getNaam())){
                    if(marktData[1].equals("BIEDT")){
                        HandelType handelType = HandelType.BIEDT;
                        Handelswaar handelswaar = new Handelswaar(marktData[2]);
                        int prijs = Integer.parseInt(marktData[3]);
                        Handel h = new Handel(stad,handelType,handelswaar,prijs);
                        handels.add(h);
                    }
                    else{
                        HandelType handelType = HandelType.VRAAGT;
                        Handelswaar handelswaar = new Handelswaar(marktData[2]);
                        int prijs = Integer.parseInt(marktData[3]);
                        Handel h = new Handel(stad,handelType,handelswaar,prijs);
                        handels.add(h);
                    }
                }
            }
        }
        Markt m = new Markt(handels);

        return  Wereld.van(k,a,m);
    }


}
