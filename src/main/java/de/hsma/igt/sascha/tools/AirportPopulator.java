package de.hsma.igt.sascha.tools;

import java.util.ArrayList;
import java.util.List;

import de.hsma.igt.sascha.controllers.AirportController;
import de.hsma.igt.sascha.models.Airport;

public class AirportPopulator {

    public static List<Airport> populateAirportsAsList(int numberOfAirports) {
    	AirportController airportController = AirportController.getAirportController();
        Airport airport = new Airport();
        List<Airport> aList = new ArrayList<>();
        String name_airpot, name_country;
        try {
            for(int k = 1; k < numberOfAirports; k++) {
                name_airpot = "Airport_" + k;
                name_country = "Country_" + k;
                aList.add(airportController.createAirport(name_airpot, name_country));
            }

        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate airport table");
            ex.printStackTrace();
            System.exit(1);
        }
        return aList;
    }
}
