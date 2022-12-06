package config;

public class ConfigFactory {

    private final static int COUNT_PARAMS = 4;

    private final static String FILE_NAME = "config.properties";

    public static Config config;

    public static Config create (String[] args) {
        if (args.length < COUNT_PARAMS) {
            config =  new ConfigFromFile (FILE_NAME);
        } else {
            config = new ConfigFromCli (args);
        }
        return config;
    }

    public static Config getConfig(){
        return config;
    }


}
