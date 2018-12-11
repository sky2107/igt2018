import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.hsma.igt.sascha.controllers.MyCustomerController;
import de.hsma.igt.sascha.models.MyCustomer;

public class MyCustomerControllerTest {
	@Before
	public void setUp() throws Exception {
		MyCustomerController custController = MyCustomerController.getControllerInstance();
		custController.deleteAllCustomer();
		custController.createCustomers();
	}

	@After
	public void tearDown() throws Exception {
		MyCustomerController custController = MyCustomerController.getControllerInstance();
		custController.deleteAllCustomer();
	}

	@Test
	(expected = NullPointerException.class)
	public void testF_deleteCustomerTest() {
		MyCustomerController controller = MyCustomerController.getControllerInstance();
		List<String> custIDs = controller.getAllCustomerIDs();
		
		System.out.println(custIDs.get(0));
		MyCustomer myCustomer = MyCustomerController.getControllerInstance().getCustomer(custIDs.get(10));
		String id = myCustomer.getC_ID();
		System.out.println(id);
		controller.deleteCustomer(id);

		myCustomer = MyCustomerController.getControllerInstance().getCustomer(id);
		System.out.println(myCustomer);
		myCustomer.getC_ID();

	}
	
    @Test
    public void testG_deleteAllCustomerTest() {
        MyCustomerController custController = MyCustomerController.getControllerInstance();
        custController.deleteAllCustomer();
    }

}
