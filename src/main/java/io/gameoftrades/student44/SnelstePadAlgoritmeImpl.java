package io.gameoftrades.student44;

import io.gameoftrades.model.algoritme.SnelstePadAlgoritme;
import io.gameoftrades.model.kaart.Coordinaat;
import io.gameoftrades.model.kaart.Kaart;
import io.gameoftrades.model.kaart.Pad;
import io.gameoftrades.model.kaart.Richting;

public class SnelstePadAlgoritmeImpl implements SnelstePadAlgoritme {

    @Override
    public Pad bereken(Kaart kaart, Coordinaat coordinaat, Coordinaat coordinaat1) {
        return new Pad() {
            @Override
            public int getTotaleTijd() {
                return 0;
            }

            @Override
            public Richting[] getBewegingen() {
                return new Richting[0];
            }

            @Override
            public Pad omgekeerd() {
                return null;
            }

            @Override
            public Coordinaat volg(Coordinaat coordinaat) {
                return null;
            }
        };
    }
}
