package io.gameoftrades.student44.SnelstePadAlgoritme;

import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

public class PadImpl implements Pad {
    private Richting[] richtingen;
    private PadImpl omgekeerd = null;
    private int totaleTijd;

    public PadImpl(Richting[] _richtingen, int _totaleTijd){

        this.richtingen = _richtingen;
        this.totaleTijd = _totaleTijd;

    }

    @Override
    public int getTotaleTijd() {
        return this.totaleTijd;
    }

    @Override
    public Richting[] getBewegingen() {
        return this.richtingen;
    }

    @Override
    public Pad omgekeerd() {
        if(this.omgekeerd!=null){
            return this.omgekeerd;
        }
        Richting[] richtingOmgekeerd = new Richting[this.richtingen.length];

        for (int i = 0; i<richtingen.length; i++){
            richtingOmgekeerd[i] = richtingen[richtingen.length-i-1].omgekeerd();
        }

        this.omgekeerd = new PadImpl(richtingOmgekeerd,this.totaleTijd);

        return this.omgekeerd;
    }


    @Override
    public Coordinaat volg(Coordinaat coordinaat) {
       for(Richting richting:richtingen){
           coordinaat = coordinaat.naar(richting);
       }
       return coordinaat;
    }
}
