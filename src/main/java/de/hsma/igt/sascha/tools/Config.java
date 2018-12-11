package de.hsma.igt.sascha.tools;


public class Config {
	public static final String PERSISTENCE_NAME = PERSISTENCE_UNITS.OGM_MONGODB.name();

    public static final Integer NUMBER_OF_FLIGHTS = 100;
    public static final Integer NUMBER_OF_AIRPORTS = 20;
    public static final Integer NUMBER_OF_SEGMENTS = 70;
    public static final Integer NUMBER_OF_CUSTOMERS = 100;
    public static final String PERSISTENCE_UNIT_NAME = PERSISTENCE_NAME;
    public static final String LOG_STORAGE_LOCATION = "C:\\Users\\FelixNavas\\" + PERSISTENCE_UNIT_NAME + "_crud_performance.txt";
    public static final Integer TRANSACTION_TIMEOUT = 900000;
    public static final String PERSIST_STORAGE_LOCATION = "C:\\Users\\FelixNavas\\customers.txt";
    public static final String PERSIST_STORAGE_OUTPUT_LOCATION = "C:\\Users\\FelixNavas\\customers_out.txt";
    public enum PERSISTENCE_UNITS {
        OGM_MYSQL, OGM_MONGODB, OGM_NEO4J, OGM_CASSANDRA, OGM_REDIS,
    }

}
