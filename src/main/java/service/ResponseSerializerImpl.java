package service;

import config.Config;
import response.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

class ResponseSerializerImpl implements ResponseSerializer {

    private final Config config;

    public ResponseSerializerImpl (Config config) {
        this.config = config;
    }

    @Override
    public byte[] serialize (HttpResponse httpResponse) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream ()) {

            outputStream.write (creteHeader (httpResponse));
            outputStream.write (httpResponse.getBody ());

            return outputStream.toByteArray ();
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    private byte[] creteHeader (HttpResponse response) {

        StringBuilder header = new StringBuilder ();
        header
                .append (config.getHttpVersion ())
                .append (" ")
                .append (response.getStatus ())
                .append ("\n")

                .append ("Content-Type: ")
                .append (response.getContentType ())
                .append ("; charset=")
                .append (config.getHttpCharset ())

                .append ("\n\n");

        return header.toString ().getBytes(StandardCharsets.UTF_8);
    }
}
