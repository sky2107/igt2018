package de.hsma.igt.sascha.controllers;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
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
import de.hsma.igt.sascha.models.MyCustomer;
import de.hsma.igt.sascha.tools.MyCustomerPopulator;
import de.hsma.igt.sascha.tools.Config;

public class MyCustomerController {
	private static final MyCustomerController customerController = new MyCustomerController();
	private static Logger logger = Logger.getRootLogger();
	TransactionManager tM = com.arjuna.ats.jta.TransactionManager.transactionManager();
	EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

	private MyCustomerController() {
	}

	public List<MyCustomer> createCustomers() {
		List<MyCustomer> myCustomers = MyCustomerPopulator.populateCustomerAsList(Config.NUMBER_OF_CUSTOMERS);

		try {
			logger.info("Create customers TA begins");
			EntityManager em = emf.createEntityManager();
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();

			long queryStart = System.currentTimeMillis();

			for (MyCustomer c : myCustomers) {
				em.persist(c);
			}

			long queryEnd = System.currentTimeMillis();

			em.flush();
			em.close();
			tM.commit();

			logger.info("Create customers TA ends");

			long queryTime = queryEnd - queryStart;

			logger.info(myCustomers.size() + " customers persisted in DB in " + queryTime + " ms.");

			String writeToFile = new String(
					Config.PERSISTENCE_UNIT_NAME + " CREATE: " + myCustomers.size() + " " + queryTime + "\n");

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
		return myCustomers;
	}

	public MyCustomer createCustomer(String C_ADDR, String C_NAME, String[] C_PHONE) {
		MyCustomer myCustomer = new MyCustomer(C_ADDR, C_NAME, C_PHONE);
		try {
			EntityManager em = emf.createEntityManager();
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();
			em.persist(myCustomer);
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
		return myCustomer;
	}

	public void updateCustomer(MyCustomer customer) {
		try {
			EntityManager em = emf.createEntityManager();
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();
			em.merge(customer);
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

	public void deleteAllCustomer() {

		List<String> myCustomerIDs;

		try {
			myCustomerIDs = getAllCustomerIDs();
//			System.out.println(myCustomerIDs);
			logger.info("Delete all customers TA begins");
			long queryStart = System.currentTimeMillis();

			int count = 0;
			for (String id : myCustomerIDs) {
//				System.out.println(id);
//				System.out.println(count++);
				deleteCustomer(id);
			}

			long queryEnd = System.currentTimeMillis();

			logger.info("Delete all customers TA ends");
			long queryTime = queryEnd - queryStart;

			logger.info(myCustomerIDs.size() + " customers successfully deleted in " + queryTime + " ms.");

			String writeToFile = new String(
					Config.PERSISTENCE_UNIT_NAME + " DELETE: " + myCustomerIDs.size() + " " + queryTime + "\n");
			Files.write(Paths.get(Config.LOG_STORAGE_LOCATION), writeToFile.getBytes(), CREATE, APPEND);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteCustomer(String myCustomerID) {

		try {
			MyCustomer costumer = MyCustomerController.getControllerInstance().getCustomer(myCustomerID);
			// System.out.println("Checking for relations...");
//			System.out.println(costumer.getBookedFlights());
			if (costumer.getBookedFlights().size() > 0) {
				costumer.setBookedFlights(new HashSet<>());
				// delete booked_flights
			}

			logger.info("Delete customer TA begins");
			EntityManager em = emf.createEntityManager();
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();

			// System.out.println("deleting.....");
			long queryStart = System.currentTimeMillis();

			MyCustomer cust = em.find(MyCustomer.class, myCustomerID);
			logger.info("Found customer: " + cust.toString());
			logger.info("Deleting customer...");
			em.remove(cust);
			long queryEnd = System.currentTimeMillis();
			em.flush();
			em.close();
			tM.commit();
			logger.info("Delete customer TA ends");

			long queryTime = queryEnd - queryStart;

			logger.info("Customer successfully deleted in " + queryTime + " ms.");

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

	public List<String> getAllCustomerIDs() {

		List<MyCustomer> myCustomerIDs = new ArrayList<>();
		List<String> returnList = new ArrayList<>();

		try {
			EntityManager em = emf.createEntityManager();

			// String queryString = new String("SELECT c.C_ID FROM Customer c");
			/*
			 * some datastores (that uses indexes like Redis, Infinispan, Cassandra) cannot
			 * operate on single return values, they always need to return the entire class
			 */

			String queryString = new String("SELECT c FROM MyCustomer c");
			Query q = em.createQuery(queryString);

			logger.info("Get all customerIDs TA begins");
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();

			long queryStart = System.currentTimeMillis();

			myCustomerIDs = q.getResultList();

			for (MyCustomer c : myCustomerIDs) {
				returnList.add(c.getC_ID());
			}

			long queryEnd = System.currentTimeMillis();

			em.flush();
			em.close();
			tM.commit();
			logger.info("Get all customerIDs TA ends");

			long queryTime = queryEnd - queryStart;

			logger.info("Found " + myCustomerIDs.size() + " customer IDs in " + queryTime + " ms.");

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

	public MyCustomer getCustomer(String myCustomerID) {
		MyCustomer myCustomer = new MyCustomer();

		try {
			EntityManager em = emf.createEntityManager();
			tM.begin();
			myCustomer = em.find(MyCustomer.class, myCustomerID);
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
		return myCustomer;
	}

	public void bookFlight(Flight flight, MyCustomer myCustomer) {
//    	try {
		myCustomer.getBookedFlights().add(flight);
		updateCustomer(myCustomer);
		flight.getCustomers().add(myCustomer);
		FlightController.getFlightController().updateFlight(flight);
//    	} catch(IndexOutOfBoundsException e) {
//    		e.printStackTrace();
//    	}

	}

	public static MyCustomerController getControllerInstance() {
		return customerController;
	}

	public void removeAll() {

		try {
			EntityManager em = emf.createEntityManager();

			String queryString = new String("DELETE FROM MyCustomer c");
			Query q = em.createQuery(queryString);
			System.out.println(q);
			logger.info("Get all customerIDs TA begins");
			tM.setTransactionTimeout(Config.TRANSACTION_TIMEOUT);
			tM.begin();

			long queryStart = System.currentTimeMillis();

			em.flush();
			em.close();
			tM.commit();
			logger.info("Deleted TA ends");
			long queryEnd = System.currentTimeMillis();

			long queryTime = queryEnd - queryStart;

			logger.info("Deleted in" + queryTime + " ms.");

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
}
