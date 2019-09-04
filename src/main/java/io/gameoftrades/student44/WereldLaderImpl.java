package io.gameoftrades.student44;

import io.gameoftrades.model.Wereld;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Stad;
import io.gameoftrades.model.lader.WereldLader;
import io.gameoftrades.model.markt.Handel;
import io.gameoftrades.model.markt.Markt;

import java.util.ArrayList;
import java.util.Scanner;

public class WereldLaderImpl implements WereldLader {

    @Override
    public Wereld laad(String resource) {
        //
        // Gebruik this.getClass().getResourceAsStream(resource) om een resource van het classpath te lezen.
        //
        // Kijk in src/test/resources voor voorbeeld kaarten.
        //
        // TODO Laad de wereld!
        //


        Scanner s = new Scanner(this.getClass().getResourceAsStream(resource));
        String firstline = s.nextLine();
        firstline.trim();
        String[] data = firstline.split(",");

        int[] intData = new int[data.length];

        for (int i = 0; i < intData.length; i++) {
            intData[i] = Integer.parseInt(data[i]);
        }

        Kaart k = Kaart.metOmvang(intData[0],intData[1]);

        ArrayList<Stad> a = new ArrayList<>();

        ArrayList<Handel> h = new ArrayList<>();

        Markt m = new Markt(h);


        Wereld.van(k,a,m);

        return null;
    }


}
