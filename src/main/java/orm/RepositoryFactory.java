package orm;

import entity.User;

public class RepositoryFactory {

    public static Repository<User> createUserRepository () {
        return new RepositoryImpl<> (User.class);
    }
}
