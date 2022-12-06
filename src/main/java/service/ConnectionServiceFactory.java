package service;

public class ConnectionServiceFactory {

    private static ConnectionService connectionService;

    public static ConnectionService createConnectionService(){
        if (connectionService == null){
            connectionService = new ConnectionServiceImpl ();
        }
        return connectionService;
    }
}
