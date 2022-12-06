package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Deque;
import java.util.LinkedList;

class SocketServiceImpl implements SocketService {

    private final Socket socket;

    SocketServiceImpl (Socket socket) {
        this.socket = socket;
    }

    public Deque<String> readRequest () {
        try {
            BufferedReader input = new BufferedReader (
                    new InputStreamReader (
                            socket.getInputStream (), StandardCharsets.UTF_8));

            while (!input.ready ()) ;

            Deque<String> request = new LinkedList<> ();
            while (input.ready ()) {
                String line = input.readLine ();
                System.out.println (line);
                request.add (line);
            }
            return request;
        } catch (IOException e) {
            throw new IllegalStateException (e);
        }
    }

    public void writeResponse (byte[] response) {
        try (OutputStream output = socket.getOutputStream ();) {
            output.write (response);
            output.flush ();
        } catch (IOException e) {
            throw new IllegalStateException (e);
        }
    }

    @Override
    public void close () {
        if (!socket.isClosed ()) {
            try {
                socket.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }
}
