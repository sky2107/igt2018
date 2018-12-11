package de.hsma.igt.sascha.transactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

import de.hsma.igt.sascha.controllers.AirportController;
import de.hsma.igt.sascha.controllers.FlightController;
import de.hsma.igt.sascha.controllers.FlightsegmentController;
import de.hsma.igt.sascha.controllers.MyCustomerController;
import de.hsma.igt.sascha.controllers.UsedFlightsegmentController;
import de.hsma.igt.sascha.models.Airport;
import de.hsma.igt.sascha.models.Flight;
import de.hsma.igt.sascha.models.Flightsegment;
import de.hsma.igt.sascha.models.MyCustomer;
import de.hsma.igt.sascha.models.UsedFlightsegment;
import de.hsma.igt.sascha.tools.Config;
import javassist.expr.Cast;

public class FlightsGenerate {
	static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		/**
		 * CREATING FLIGHTS only once 100
		 */
		List<Flight> flights = FlightController.getFlightController().createFlightsAsList();
		if (flights.size() > 0) {
			for (Flight flight : flights) {
				System.out.println(flight.getF_AIRPLANE_TYPE());
			}
		}
//		List<Flight> flights = FlightController.getFlightController().getAllFlightsAsList();
//		System.out.println(flights.iterator());
//		System.out.println(flights.iterator());
//
//		for (Flight flight : flights) {
//			System.out.println(flight.getF_AIRPLANE_TYPE());
//			flights.iterator();
//		}
		if (Config.PERSISTENCE_UNIT_NAME == "OGM_CASSANDRA" || Config.PERSISTENCE_UNIT_NAME == "OGM_REDIS") {
			int airport_count = 1;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
			AirportController.getAirportController().createAirport("Airport" + airport_count, "Contry" + airport_count);
			airport_count++;
		} else {
			segmentsCreating();
		}

	}

	/**
	 * 
	 */
	private static void segmentsCreating() {
		List<String> air = AirportController.getAirportController().getAllAirportIDs();
		List<Flight> flights = FlightController.getFlightController().getAllFlightsAsList();
		List<Airport> airports1 = new ArrayList<>();
		List<Airport> airports2 = new ArrayList<>();
		List<Flightsegment> segments = new ArrayList<>();
		List<UsedFlightsegment> usedseg = new ArrayList<>();
		;
		int change = 0;
		for (String id : air) {
			System.out.println(id);
			Airport e = AirportController.getAirportController().getAirportByID(id);
			System.out.println(e);
			if (change % 2 == 0) {
				airports1.add(e);
			} else {
				airports2.add(e);
			}
			change++;
		}
		System.out.println(airports1.size());
		for (int i = 0; i < airports1.size(); i++) {
			segments.add(FlightsegmentController.getFlightsegmentController().createFlightsegment(airports1.get(i),
					airports2.get(i), 3000D));
		}

		for (int i = 0; i < segments.size(); i++) {
			UsedFlightsegment usedSegment = UsedFlightsegmentController.getUsedFlightsegmentController()
					.createUsedFlightsegment(flights.get(i), segments.get(i), segments.get(i).getFS_START_AIRPORT(),
							segments.get(i).getFS_GOAL_AIRPORT());
			usedseg.add(usedSegment);
		}

	}
}
