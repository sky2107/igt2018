package de.hsma.igt.sascha.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.hsma.igt.sascha.models.MyCustomer;

public class MyCustomerPopulator {
	public static Random rand = new Random();

	public static List<MyCustomer> populateCustomerAsList(int numberOfCustomers) {

		String C_ADDR;
		Double C_FLOWN_MILES;
		Double C_CURRENT_FLOWN_MILES;
		String C_NAME;

		List<MyCustomer> cList = new ArrayList<>();

		try {
			for (long k = 0; k < numberOfCustomers; k++) {
				C_ADDR = "Address_" + k;
				String[] phoneTypes = { "Samsung", "Nokia", "Blackbery" };
				C_FLOWN_MILES = Math.random() * 1000;
				C_CURRENT_FLOWN_MILES = Math.random() * 700;
				C_NAME = "Customer" + k;
				MyCustomer customer = new MyCustomer(C_ADDR, C_NAME, phoneTypes);
				customer.setC_FLOWN_MILES(C_FLOWN_MILES);
				customer.setC_CURRENT_FLOWN_MILES(C_CURRENT_FLOWN_MILES);
				cList.add(customer);

			}
		} catch (java.lang.Exception ex) {
			System.err.println("Unable to populate CUSTOMER table");
			ex.printStackTrace();
			System.exit(1);
		}
		return cList;
	}

}
