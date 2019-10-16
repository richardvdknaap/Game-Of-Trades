package io.gameoftrades.student44;

import io.gameoftrades.debug.Debuggable;
import io.gameoftrades.debug.Debugger;
import io.gameoftrades.model.Handelaar;
import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.algoritme.StedenTourAlgoritme;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Stad;

import java.util.ArrayList;
import java.util.List;

public class StedenTourAlgoritmeImpl implements StedenTourAlgoritme, Debuggable {
    private Kaart kaart;
    private List<Stad> steden;
    private ArrayList<Stad> stadGehad;
    private Debugger debugger;
    private Stad currentStad;
    private Stad nextStad;
    private Handelaar handelaar;
    private SnelstePadAlgoritme algoritme;
    private SnelstePadAlgoritmeImpl nieuweAlgoritme;


    @Override
    public List<Stad> bereken(Kaart _kaart, List<Stad> _list) {
        this.kaart = _kaart;
        this.steden= _list;
        this.stadGehad = new ArrayList<>();
        this.currentStad = steden.get(0);
        this.handelaar = new HandelaarImpl();
        this.algoritme = handelaar.nieuwSnelstePadAlgoritme();

        stadGehad.add(currentStad);
        while(stadGehad.size() != steden.size()){
            currentStad = getNextStad(stadGehad);
            System.out.println(currentStad.getNaam());
        }
       
        debugger.debugSteden(kaart, stadGehad);
        return stadGehad;
    }

    public Stad getNextStad(ArrayList<Stad> stadGehad){
        Stad dichsteStad = null;
        double cost= -1;
        for(Stad stad: steden){
           if(stadGehad.contains(stad)){
               continue;
           }
           Pad pad = algoritme.bereken(kaart, currentStad.getCoordinaat(), stad.getCoordinaat());
           if(cost != -1 && pad.getTotaleTijd() >= cost){
               continue;
           }
           dichsteStad = stad;
           cost = pad.getTotaleTijd();


        }
        stadGehad.add(dichsteStad);
        return dichsteStad;
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }
}
