package response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;

public class HtmlPage {

    private byte[] body;

    private HtmlPage () {
    }

    public byte[] getBody(){
        return body;
    }

    public static Builder createBilder () {
        return new Builder ();
    }

    public static class Builder {

        private final HtmlPage contentHtml;

        private final HashMap<String, String> attributes = new HashMap<> ();

        private Path path;

        private Builder () {
            this.contentHtml = new HtmlPage ();
        }

        public Builder withPath (Path path) {
            this.path = path;
            return this;
        }

        public Builder withAttribute (String key, String value) {
            this.attributes.put (key, value);
            return this;
        }

        public HtmlPage build () {
            if (path == null) {
                throw new NullPointerException ("File path is null");
            }
            this.contentHtml.body = createBody ();
            return contentHtml;
        }

        public byte[] createBody () {
            try (BufferedReader buffered = new BufferedReader (new FileReader (path.toFile ()));) {
                StringBuffer html = new StringBuffer ();
                buffered.lines ().forEach (line -> {
                    if (!attributes.isEmpty () && line.contains ("$") && !attributes.isEmpty ()) {
                        line = replaсeAttribute (line);
                    }
                    html.append (line);
                });
                return html.toString ().getBytes (StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException (e);
            }
        }

        private String replaсeAttribute (String line) {
            String key = line.split ("\\{|\\}")[1];
            if (attributes.containsKey (key)) {
                String value = attributes.remove (key);
                line = line.replace ("${" + key + "}", value);
            }
            return line;
        }

    }

}
