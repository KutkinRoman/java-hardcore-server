import config.Config;
import config.ConfigFactory;
import handler.method.MethodHandlerFactory;
import handler.request.RequestHandlerFactory;
import service.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private final Config config;

    private WebServer (Config config) {
        this.config = config;
    }

    public void start () {

        try (ServerSocket serverSocket = new ServerSocket (config.getPort ())) {
            System.out.printf ("Server started. Port: %s.\n", config.getPort ());

            while (true) {
                Socket socket = serverSocket.accept ();
                System.out.println ("New client connected!");

                new Thread (
                        RequestHandlerFactory.createRequestHandler (
                                SocketServiceFactory.createSocketService (socket),
                                RequestParserFactory.createRequestParser (),
                                ResponseSerializerFactory.createResponseSerializer (config),
                                MethodHandlerFactory.createMethodHandler (
                                        ReflectionServiceFactory.createReflectionService ()
                                )
                        )).start ();
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }

    public static WebServer create (Config config) {
        return new WebServer (config);
    }

    public static void main (String[] args) {
        WebServer.create (ConfigFactory.create (args)).start ();
    }
}
