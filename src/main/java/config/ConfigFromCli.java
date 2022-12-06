package config;

class ConfigFromCli implements Config{

    private int port;

    private String wwwHome;

    private String httpVersion;

    private String httpCharset;

    public ConfigFromCli (String[] args) {
        this.port = Integer.parseInt (args[0]);
        this.wwwHome = args[1];
        this.httpVersion = args[2];
        this.httpCharset = args[3];
    }

    @Override
    public String getWwwHome () {
        return wwwHome;
    }

    @Override
    public Integer getPort () {
        return this.port;
    }

    @Override
    public String getHttpVersion () {
        return httpVersion;
    }

    @Override
    public String getHttpCharset () {
        return httpCharset;
    }
}
