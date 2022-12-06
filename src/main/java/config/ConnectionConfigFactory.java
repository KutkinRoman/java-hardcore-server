package config;

public class ConnectionConfigFactory {

    private final static String FILE_NAME = "connection.properties";

    public static ConnectionConfig createConnectionConfig () {
        return new ConnectionConfigFromFile (FILE_NAME);
    }
}
