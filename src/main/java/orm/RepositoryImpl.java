package orm;

import service.ConnectionServiceFactory;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

class RepositoryImpl <T extends Entity> implements Repository<T>{

    private final Connection conn;

    private final EntityMapper<T> mapper;

    private final Class<T> clazz;

    private UnitOfWork<T> unitOfWork;

    public RepositoryImpl (Class<T> clazz) {
        this.clazz = clazz;
        this.conn = ConnectionServiceFactory.createConnectionService ().getConnector ();
        this.mapper = new EntityMapper<> (conn, clazz);
        this.unitOfWork = new UnitOfWork<> (conn, clazz);
    }

    public List<T> findAll () {
        return mapper.findAll ();
    }

    public Optional<T> findById (long id) {
        return mapper.findById (id);
    }

    public void beginTransaction () {
        this.unitOfWork = new UnitOfWork<> (this.conn, this.clazz);
    }

    public void insert (T T) {
        unitOfWork.registerNew (T);
    }

    public void update (T T) {
        unitOfWork.registerUpdate (T);
    }

    public void delete (T T) {
        unitOfWork.registerDelete (T);
    }

    public void commitTransaction () {
        unitOfWork.commit ();
    }

}
