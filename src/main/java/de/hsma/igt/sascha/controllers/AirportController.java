package de.hsma.igt.sascha.controllers;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

import de.hsma.igt.sascha.models.Airport;
import de.hsma.igt.sascha.models.Flight;
import de.hsma.igt.sascha.models.Flightsegment;
import de.hsma.igt.sascha.models.MyCustomer;
import de.hsma.igt.sascha.models.UsedFlightsegment;
import de.hsma.igt.sascha.tools.AirportPopulator;
import de.hsma.igt.sascha.tools.Config;

public class AirportController {
    private static final AirportController airportController = new AirportController();
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

    private AirportController(){};

    public Airport getAirport(String id) {
        Airport airport = new Airport();
        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            airport = em.find(Airport.class, id);
            em.flush();
            em.close();
            tM.commit();
        }catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return airport;
    }

    public Airport getAirportByID(String id) {
        Airport airport = new Airport();
        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            airport = em.find(Airport.class, id);
            em.flush();
            em.close();
            tM.commit();
        }catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return airport;
    }

    public List<Airport> createAirportsAsList() {
        List<Airport> airports = AirportPopulator.populateAirportsAsList(Config.NUMBER_OF_AIRPORTS);
        try {
            logger.info("Create airports TA begins");
            EntityManager em = emf.createEntityManager();
            // impedance missmatch safty net
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            long queryStart = System.currentTimeMillis();

            for (Airport a : airports) {
            	// checking for duplicates
                em.persist(a);
            }
            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();

            logger.info("Create airports TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(airports.size() + " airports persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " CREATE: " + airports.size() + " " + queryTime + "\n");

            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return airports;
    }

    public Airport createAirport(String A_NAME, String A_COUNTRY) {
        Airport airport = new Airport(A_NAME, A_COUNTRY);
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.persist(airport);
            em.flush();
            em.close();
            tM.commit();

        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return airport;
    }

    public void updateAirport(Airport airport) {
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.merge(airport);
            em.flush();
            em.close();
            tM.commit();
        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("null")
	public List<Flight> findFlights(String start, String goal) {
        List<Flight> fList = FlightController.getFlightController().getAllFlightsAsList();
        List<Flight> foundFlights = new ArrayList<>();
        List<UsedFlightsegment> segments = new ArrayList<>();
        int index = 0;
        for(Flight f : fList) {
            segments = f.getUsedFlightsegment();
            if(segments!=null || !segments.isEmpty()) {
            	System.out.println("_____________");
            	String startingAirport = segments.get(index).getStartingAirport();
            	String destinationAirport = segments.get(index).getDestinationAirport();
            	String stopover = "";
            	//Ein Flug besteht aus maximal 2 Flugsegmenten
            	//Start und Ziel liegen innerhalb eines Segments
            	if(startingAirport.equals(start) && destinationAirport.equals(goal)){
            		foundFlights.add(f);
            	}
            	//Flugsegment enthält Startflughafen
            	else if(startingAirport.equals(start) && !destinationAirport.equals(goal)) {
            		foundFlights.add(f);
            		stopover = destinationAirport;
            	}
            	//Flugsegment enthält Zielflughafen
            	else if (startingAirport.equals(stopover) && destinationAirport.equals(goal)) {
            		foundFlights.add(f);
            	}
            }
        }
        index = 0;
        return foundFlights;
    }

    public List<String> getAllAirportIDs() {

        List<Airport> airportIDs = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT c.C_ID FROM Customer c");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT c FROM Airport c");
            Query q = em.createQuery(queryString);


            logger.info("Get all airportIDs TA begins");
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            airportIDs = q.getResultList();

            for (Airport c : airportIDs) {
                returnList.add(c.getA_ID());
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();
            logger.info("Get all customerIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + airportIDs.size() + " customer IDs in " + queryTime + " ms.");


        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return returnList;
    }
    
    public List<String> getAllAirportNames() {

        List<Airport> airportIDs = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT c.C_ID FROM Customer c");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT c FROM Airport c");
            Query q = em.createQuery(queryString);


            logger.info("Get all airportIDs TA begins");
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            airportIDs = q.getResultList();

            for (Airport c : airportIDs) {
                returnList.add(c.getA_NAME());
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();
            logger.info("Get all customerIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + airportIDs.size() + " customer names in " + queryTime + " ms.");


        } catch (NotSupportedException e) {
            e.printStackTrace();
        } catch (SystemException e) {
            e.printStackTrace();
        } catch (HeuristicMixedException e) {
            e.printStackTrace();
        } catch (HeuristicRollbackException e) {
            e.printStackTrace();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        return returnList;
    }
    public static AirportController getAirportController() {
        return airportController;
    }
}

