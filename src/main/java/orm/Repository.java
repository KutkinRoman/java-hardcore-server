package orm;

import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity> {

    Optional<T> findById (long id);

    List<T> findAll ();

    void beginTransaction ();

    void insert (T entity);

    void update (T entity);

    void delete (T entity);

    void commitTransaction ();
}
