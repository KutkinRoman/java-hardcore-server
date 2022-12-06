package config;

class ConnectionConfigFromFile extends AbstractConfigFromFile implements ConnectionConfig {

    public ConnectionConfigFromFile (String fileName) {
        super (fileName);
    }

    @Override
    public String getDataBaseLocal () {
        return getProperty ("data.base.local");
    }

    @Override
    public String getDataBasePort () {
        return getProperty ("data.base.port");
    }

    @Override
    public String getDataBaseName () {
        return getProperty ("data.base.name");
    }

    @Override
    public String getDataBaseUserName () {
        return getProperty ("data.base.username");
    }

    @Override
    public String getDataBasePassword () {
        return getProperty ("data.base.password");
    }
}
