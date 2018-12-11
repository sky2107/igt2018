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
import de.hsma.igt.sascha.models.Flight;
import de.hsma.igt.sascha.models.Flightsegment;
import de.hsma.igt.sascha.models.UsedFlightsegment;
import de.hsma.igt.sascha.tools.Config;

public class UsedFlightsegmentController {
	private static final UsedFlightsegmentController usedFlightsegmentController = new UsedFlightsegmentController();
    private static Logger logger = Logger.getRootLogger();
    TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

    private UsedFlightsegmentController(){}

    public UsedFlightsegment createUsedFlightsegment(Flight flight, Flightsegment flightsegment, Airport startingAirport, Airport destinationAirport) {
        UsedFlightsegment usedSegment = new UsedFlightsegment(flight, flightsegment, flightsegment.getFS_START_AIRPORT(), flightsegment.getFS_GOAL_AIRPORT());
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.persist(usedSegment);
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
        return usedSegment;
    }
    public void updateUsedFlightsegment(UsedFlightsegment usedFlightsegment) {
        try {
            EntityManager em = emf.createEntityManager();
            tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
            tM.begin();
            em.merge(usedFlightsegment);
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
    public static UsedFlightsegmentController getUsedFlightsegmentController() {
        return usedFlightsegmentController;
    }
}
