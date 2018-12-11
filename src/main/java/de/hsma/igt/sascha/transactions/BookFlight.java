package de.hsma.igt.sascha.transactions;

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
import de.hsma.igt.sascha.tools.AirportPopulator;
import de.hsma.igt.sascha.tools.Config;
import de.hsma.igt.sascha.tools.FlightPopulator;

public class BookFlight {
	static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		/**
		 * DELETE ONE CUSTOMER
		 */
		// deleteOneCustomer("Customer70");
		
		initializeDatabase();
//		// check first for customer clones
		Boolean notskip = true;
		List<String> customerList = MyCustomerController.getControllerInstance().getAllCustomerIDs();
		for (String clone : customerList) {
			String check = MyCustomerController.getControllerInstance().getCustomer(clone).getC_NAME();
			if(check == "Customer1") {
				notskip = false;
			}
			
		}
//		/**
//		 * CREATE CUSTOMERS
//		 */
//		// CREATING CUSTOMERS automatically
		if(notskip) {
			List<MyCustomer> myCustomers = MyCustomerController.getControllerInstance().createCustomers();
			for (MyCustomer myCustomer : myCustomers) {
				System.out.println(myCustomer.getC_NAME());
			}
		} 
		
		MyCustomerController myCustomerController = MyCustomerController.getControllerInstance();

		String name = "Alexander Dum";
		String adress = "Heddesheim 3";
		String[] phoneTypes = { "Samsung", "Nokia", "Blackbery" };

		System.out.println("Register customer");
		Boolean checkIfCustomerExistNot = true;
		List<String> elementsOfCustomers = MyCustomerController.getControllerInstance().getAllCustomerIDs();
		
		MyCustomer customer = null;
		for (String clone : elementsOfCustomers) {
			String check = MyCustomerController.getControllerInstance().getCustomer(clone).getC_NAME();
			if(check == name) {
				checkIfCustomerExistNot = false;
				customer = MyCustomerController.getControllerInstance().getCustomer(clone);
			}
			
		}
		if (checkIfCustomerExistNot) {
			customer = myCustomerController.createCustomer(adress, name, phoneTypes);
		}
		System.out.println("Choose start and goal airports");
		String start = "Frankfurt Hahn";
		String goal = "Narita International Airport";
		System.out.println("Search for flights");
		List<Flight> flights = AirportController.getAirportController().findFlights(start, goal);
		System.out.println("Book flight");
		// myCustomerController.bookFlight(flights.get(0), customer);
		for (Flight flight : flights) {
			System.out.println(flight);
		}
		
		
		
		
		System.out.println(flights.size());
		if (flights.size() > 0) {
			if(checkIfCustomerExistNot) {
				myCustomerController.bookFlight(flights.get(0), customer);
			}
			
			// printing all cutomer ids
			for (String custi : myCustomerController.getAllCustomerIDs()) {
				// System.out.println(custi);
			}
		}
		// myCustomerController.bookFlight(flights.get(0), customer);
	}

	/**
	 * 
	 * @param name
	 */
	private static void deleteOneCustomer(String name) {
		List<String> cc = MyCustomerController.getControllerInstance().getAllCustomerIDs();
		Boolean deleted = false;
		for (String iter : cc) {
			MyCustomer checkName = MyCustomerController.getControllerInstance().getCustomer(iter);
			String para = checkName.getC_NAME();
			
			if(name.equals(para)) {
				MyCustomerController.getControllerInstance().deleteCustomer(iter);
				deleted = true;
			}
		}
		if(deleted) {
			System.out.println("Deleted");
		} else {
			System.out.println("Not deleted Customer does not exist!");

		}
	}

	
	public static void initializeDatabase() {
		AirportController airportController = AirportController.getAirportController();
		FlightController flightController = FlightController.getFlightController();
		FlightsegmentController flightsegmentController = FlightsegmentController.getFlightsegmentController();
		UsedFlightsegmentController usedFlightsegmentController = UsedFlightsegmentController
				.getUsedFlightsegmentController();

		// Check if it goes one time threw
		
		if(Config.PERSISTENCE_UNIT_NAME == "OGM_CASSANDRA" || Config.PERSISTENCE_UNIT_NAME == "OGM_REDIS") {
			/**
			 * CREATE AIRPORT
			 */
			// 20 airports airpot1 till airport30 + country_1 and so on
			// List<Airport> airports = AirportController.getAirportController().createAirportsAsList();
		} else {
			List<String> air = AirportController.getAirportController().getAllAirportNames(); // .getAirport("02100361-50f6-4ff8-a3ff-331e86505c29");
			
			/**
			 * CREATE AIRPORT
			 */
			// 20 airports airpot1 till airport30 + country_1 and so on
			System.out.println(air.size());
			if (air.size() == 0) {
				List<Airport> airports = AirportController.getAirportController().createAirportsAsList();
			}
		}
		

		
		/**
		 * CREATING FLIGHTS
		 */
//		List<Flight> flights = FlightController.getFlightController().createFlightsAsList();
//		
//		for (Flight flight : flights) {
//			System.out.println(flight.getF_AIRPLANE_TYPE());
//		}
//		
		
		System.out.println("--------------------------------------");
		Airport airport1 = airportController.createAirport("Frankfurt Hahn", "DE");
		Airport airport2 = airportController.createAirport("Dubai International Airport", "VAE");
		Airport airport3 = airportController.createAirport("Narita International Airport", "JPN");
		Airport airport4 = airportController.createAirport("Berlin Airport", "DE");
		Airport airport5 = airportController.createAirport("Sheremetyevo International Airport", "RUS");

		Flightsegment segment1 = flightsegmentController.createFlightsegment(airport1, airport2, 3000D);
		Flightsegment segment2 = flightsegmentController.createFlightsegment(airport2, airport3, 3000D);
		Flightsegment segment3 = flightsegmentController.createFlightsegment(airport4, airport5, 3000D);

		// List<Flight> flights = FlightPopulator.populateFlightsAsList(30);

		Flight flight1 = flightController.createFlight("BOEING777", new Date(), new Date(), 270, 100D, 70, 250D);
		// Flight flight2 = flightController.createFlight("BOEING777",new Date(), new
		// Date(), 270, 100D, 70, 250D);

		UsedFlightsegment usedSegment1 = new UsedFlightsegment(flight1, segment1, segment1.getFS_START_AIRPORT(),
				segment1.getFS_GOAL_AIRPORT());
		UsedFlightsegment usedSegment2 = new UsedFlightsegment(flight1, segment2, segment2.getFS_START_AIRPORT(),
				segment2.getFS_GOAL_AIRPORT());

		usedFlightsegmentController.updateUsedFlightsegment(usedSegment1);
		usedFlightsegmentController.updateUsedFlightsegment(usedSegment2);

		flight1.setUsedFlightsegment(usedSegment1);
		flight1.setUsedFlightsegment(usedSegment2);
		segment1.setUsedFlightsegment(usedSegment1);
		segment1.setUsedFlightsegment(usedSegment2);

		flightsegmentController.updateFlightsegment(segment1);
		flightsegmentController.updateFlightsegment(segment2);
		flightsegmentController.updateFlightsegment(segment3);

		flightController.updateFlight(flight1);
//        flightController.updateFlight(flight2);

		airport1.getFlightsegmentsStart().add(segment1);
		airport2.getFlightsegmentsGoal().add(segment1);
		airport2.getFlightsegmentsStart().add(segment2);
		airport3.getFlightsegmentsGoal().add(segment2);
		airport4.getFlightsegmentsStart().add(segment3);
		airport5.getFlightsegmentsGoal().add(segment3);
		airportController.updateAirport(airport1);
		airportController.updateAirport(airport2);
		airportController.updateAirport(airport3);
		airportController.updateAirport(airport4);
		airportController.updateAirport(airport5);
	}

	private static void deleteAllCustomers() {
		MyCustomerController.getControllerInstance().deleteAllCustomer();;	
	}
}
