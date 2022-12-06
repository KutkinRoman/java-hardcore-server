package service;

public class EntityParserFactory {
    
    public static EntityParser createEntityParser(){
        return  new EntityParserImpl ();
    }
}
