import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import de.hsma.igt.sascha.tools.Config;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfigReaderTest {

    @Test
    public void testA_readConfigFile() {

        int numberOfCustomers = Config.NUMBER_OF_CUSTOMERS;

        assertEquals(100, numberOfCustomers, 0.0001);

    }


}
