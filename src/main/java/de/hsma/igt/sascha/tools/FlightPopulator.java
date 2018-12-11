package de.hsma.igt.sascha.tools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.hsma.igt.sascha.models.Flight;

public class FlightPopulator {
    public static Random rand = new Random();

    public static List<Flight> populateFlightsAsList(int numberOfFlights) {

        String F_AIRPLANE_TYPE;
        Date F_DEPARTURE;
        Date F_ARRIVAL;
        Integer F_EC_SEATS;
        Double F_EC_PRICE;
        Integer F_FC_SEATS;
        Double F_FC_PRICE;

        
        List<Flight> fList = new ArrayList<>();

        try {
            for(long k = 0; k < numberOfFlights; k++) {
            	Flight flight = new Flight();
                F_AIRPLANE_TYPE = "BOEING777_" + k;
                F_DEPARTURE = new Date();
                F_ARRIVAL = new Date(F_DEPARTURE.getTime() + 10000000);
                F_EC_SEATS = 100;
                F_FC_SEATS = 20;
                F_EC_PRICE = 150.0;
                F_FC_PRICE = 370.0;

                flight.setF_AIRPLANE_TYPE(F_AIRPLANE_TYPE);
                flight.setF_DEPARTURE(F_DEPARTURE);
                flight.setF_ARRIVAL(F_ARRIVAL);
                flight.setF_EC_SEATS(F_EC_SEATS);
                flight.setF_FC_SEATS(F_FC_SEATS);
                flight.setF_EC_PRICE(F_EC_PRICE);
                flight.setF_FC_PRICE(F_FC_PRICE);
                fList.add(flight);
            }


        } catch (java.lang.Exception ex){
            System.err.println("Unable to populate flight table");
            ex.printStackTrace();
            System.exit(1);
        }
        return fList;
    }

}