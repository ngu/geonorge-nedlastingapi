package no.geonorge.nedlasting.data.upgrade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DbUpgrade {

    private DbUpgrade() {

    }

    public static void upgrade() {
        for (EntityUpgrade eu : getEntityUpgrades()) {
            eu.upgrade();
        }
    }

    private static List<EntityUpgrade> getEntityUpgrades() {
        List<EntityUpgrade> eus = new ArrayList<>();
        eus.add(new ProjectionUpgrade());
        return Collections.unmodifiableList(eus);
    }

}
