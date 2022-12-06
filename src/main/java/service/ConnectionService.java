package service;

import java.sql.Connection;

public interface  ConnectionService extends Cloneable{

    Connection getConnector();

    void close ();

}
