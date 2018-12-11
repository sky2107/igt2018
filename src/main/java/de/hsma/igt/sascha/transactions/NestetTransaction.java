package de.hsma.igt.sascha.transactions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;

import de.hsma.igt.sascha.controllers.MyCustomerController;
import de.hsma.igt.sascha.models.MyCustomer;
import de.hsma.igt.sascha.tools.Config;

public class NestetTransaction {
	static TransactionManager tm = com.arjuna.ats.jta.TransactionManager.transactionManager();
	static EntityManagerFactory emf = Persistence.createEntityManagerFactory(Config.PERSISTENCE_UNIT_NAME);

	public static void main(String[] args) {

		String[] handys1 = { "Nokia", "Sony" };
		String[] handys2 = { "Samsung", "Apfelfon" };

		MyCustomer c1 = new MyCustomer("Adress1", "John Doe", handys1);
		MyCustomer c2 = new MyCustomer("Adress2", "Dohn Joe", handys2);

	}
}
