package de.hsma.igt.sascha.controllers;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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

import de.hsma.igt.sascha.models.Flight;
import de.hsma.igt.sascha.tools.FlightPopulator;
import de.hsma.igt.sascha.tools.Config;

public class FlightController {
    private static final FlightController flightController = new FlightController();
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

    private FlightController(){}

    public List<Flight> createFlightsAsList() {
        List<Flight> fList = FlightPopulator.populateFlightsAsList(Config.NUMBER_OF_FLIGHTS);
        try {
            logger.info("Create flights TA begins");
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            long queryStart = System.currentTimeMillis();

            for (Flight f : fList) {
                em.persist(f);
            }
            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();

            logger.info("Create customers TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info(fList.size() + " flights persisted in DB in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " CREATE: " + fList.size() + " " + queryTime + "\n");

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
        return fList;
    }

    public Flight createFlight(String F_AIRPLANE_TYPE, Date F_DEPARTURE, Date F_ARRIVAL, Integer F_EC_SEATS, Double F_EC_PRICE, Integer F_FC_SEATS, Double F_FC_PRICE) {
        Flight flight = new Flight(F_AIRPLANE_TYPE, F_DEPARTURE, F_ARRIVAL, F_EC_SEATS, F_EC_PRICE, F_FC_SEATS, F_FC_PRICE);
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.persist(flight);
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
        return flight;
    }

    public Flight getFlight(String flightID) {
        Flight flight = new Flight();
        try {
            EntityManager em = emf.createEntityManager();
            tM.begin();
            flight = em.find(Flight.class, flightID);
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
        return flight;
    }

    public List<String> getAllFlightIDs() {
        List<Flight> flightIDs = new ArrayList<>();
        List<String> returnList = new ArrayList<>();

        try {
            EntityManager em = emf.createEntityManager();

            //String queryString = new String("SELECT f.F_ID FROM Flight f");
            /*
            some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot operate on single return values,
            they always need to return the entire class
             */

            String queryString = new String("SELECT f FROM Flight f");
            Query q = em.createQuery(queryString);


            logger.info("Get all flightIDs TA begins");
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();

            long queryStart = System.currentTimeMillis();

            flightIDs = q.getResultList();

            for (Flight f : flightIDs) {
                returnList.add(f.getF_ID());
            }

            long queryEnd = System.currentTimeMillis();

            em.flush();
            em.close();
            tM.commit();
            logger.info("Get all flightIDs TA ends");

            long queryTime = queryEnd - queryStart;

            logger.info("Found " + flightIDs.size() + " flight IDs in " + queryTime + " ms.");


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

    public void deleteFlight(String flightID){
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();

            Flight flight = em.find(Flight.class, flightID);
            em.remove(flight);
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

    public void deleteAllFlights(){
        List<String> flightIDs;

        try {
            flightIDs = getAllFlightIDs();
            logger.info("Delete all flightsTA begins");
            long queryStart = System.currentTimeMillis();

            for (String id : flightIDs) {
                deleteFlight(id);
            }

            long queryEnd = System.currentTimeMillis();

            logger.info("Delete all flights TA ends");
            long queryTime = queryEnd - queryStart;

            logger.info(flightIDs.size() + " flights successfully deleted in " + queryTime + " ms.");

            String writeToFile = new String(Config.PERSISTENCE_UNIT_NAME + " DELETE: " + flightIDs.size() + " " + queryTime + "\n");
            Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFlight(Flight flight) {
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.merge(flight);
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

    public List<Flight> getAllFlightsAsList() {
        List<Flight> fList = new ArrayList<>();
        try{
            EntityManager em = emf.createEntityManager();
            String queryString = new String("SELECT f FROM Flight f");
            Query q = em.createQuery(queryString);
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            fList = q.getResultList();
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
        return fList;
    }

    public static FlightController getFlightController() {
        return flightController;
    }

}
