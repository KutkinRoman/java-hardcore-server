package service;

import config.ConnectionConfig;
import config.ConnectionConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionConfig config;
    private final Connection connection;

    public ConnectionServiceImpl () {
        this.config = ConnectionConfigFactory.createConnectionConfig ();
        this.connection = createConnection ();
    }

    private Connection createConnection () {
        String url = String.format ("jdbc:mysql://%s:%s/%s",
                config.getDataBaseLocal (),
                config.getDataBasePort (),
                config.getDataBaseName ());
        try {
            System.out.println (String.format ("Connect Date Base URL [%s]", url));
            return DriverManager.getConnection (url, config.getDataBaseUserName (), config.getDataBasePassword ());
        } catch (SQLException e) {
            throw new IllegalStateException (e);
        }
    }

    @Override
    public Connection getConnector () {
        return this.connection;
    }

    @Override
    public void close () {
        try {
            if (!connection.isClosed ()) {
                connection.close ();
            }
        } catch (SQLException e) {
            throw new IllegalStateException (e);
        }
    }
}
