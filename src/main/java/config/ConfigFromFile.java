package config;

class ConfigFromFile extends AbstractConfigFromFile implements Config {


    public ConfigFromFile (String fileName) {
        super (fileName);
    }

    @Override
    public String getWwwHome () {
        return getProperty ("www.home");
    }

    @Override
    public Integer getPort () {
        return Integer.parseInt (getProperty ("server.port"));
    }

    @Override
    public String getHttpVersion () {
        return getProperty ("http.version");
    }

    @Override
    public String getHttpCharset () {
        return getProperty ("http.charset");
    }


}
