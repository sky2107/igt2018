package de.hsma.igt.sascha.tools;

import java.util.ArrayList;
import java.util.List;

import de.hsma.igt.sascha.models.Flightsegment;

public class FlightsegmentPopulator {

    public static List<Flightsegment> populateFlightsAsList(int numberOfsegments) {

        Flightsegment flightsegment = new Flightsegment();
        List<Flightsegment> fList = new ArrayList<>();
        List<Flightsegment> flightsegments = new ArrayList<>();

        try {
            for(int k = 1; k < numberOfsegments; k++) {

            }

        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate flightsegment table");
            ex.printStackTrace();
            System.exit(1);
        }
        return fList;
    }
}
