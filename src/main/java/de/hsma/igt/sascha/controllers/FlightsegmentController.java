package de.hsma.igt.sascha.controllers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import org.apache.log4j.Logger;

import de.hsma.igt.sascha.models.Airport;
import de.hsma.igt.sascha.models.Flightsegment;
import de.hsma.igt.sascha.tools.Config;

public class FlightsegmentController {
    private static final FlightsegmentController flightsegmentController = new FlightsegmentController();
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

    private FlightsegmentController(){}

    public Flightsegment createFlightsegment(Airport FS_START_AIRPORT, Airport FS_GOAL_AIRPORT, double FS_DISTANCE) {
        Flightsegment flightsegment = new Flightsegment(FS_START_AIRPORT, FS_GOAL_AIRPORT, FS_DISTANCE);
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.persist(flightsegment);
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
        return flightsegment;
    }
    public void updateFlightsegment(Flightsegment flightsegment) {
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.merge(flightsegment);
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
    public static FlightsegmentController getFlightsegmentController() {
        return flightsegmentController;
    }
}
