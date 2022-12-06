package orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EntityMapper<T extends Entity> {

    private final Class<T> clazz;
    private final Connection conn;
    private final String tableName;

    public EntityMapper (Connection connection, Class<T> clazz) {
        this.conn = connection;
        this.clazz = clazz;
        this.tableName = clazz.getAnnotation (TableName.class).value ();
    }

    public Optional<T> findById (Long id) {
        try {
            String sql = String.format ("SELECT * FROM `%s` WHERE id = '%s';", this.tableName, id);
            ResultSet rs = executeQuery (sql);
            if (rs.next ()) {
                return Optional.of (map (rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException (e);
        }
        return Optional.empty ();
    }

    public List<T> findAll () {
        List<T> list = new ArrayList<> ();
        try {
            String sql = String.format ("SELECT * FROM `%s`;", this.tableName);
            ResultSet rs = executeQuery (sql);
            while (rs.next ()) {
                list.add (map (rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException (e);
        }
        return list;
    }

    public T map (ResultSet rs) throws SQLException {
        try {
            Object entity = clazz.getConstructor ().newInstance ();
            for (Field field : entity.getClass ().getDeclaredFields ()) {
                ColumnName columnName = field.getAnnotation (ColumnName.class);
                if (columnName != null) {
                    Object value = rs.getObject (columnName.value ());
                    if (value != null) {
                        createField (entity, field, value);
                    }
                }
            }
            return ( T ) entity;
        } catch (Exception e) {
            throw new IllegalStateException (e);
        }
    }

    private ResultSet executeQuery (String sql) throws SQLException {
        Statement stat = conn.createStatement ();
        return stat.executeQuery (sql);
    }

    private void createField (Object object, Field field, Object value) throws Exception {
        field.setAccessible (true);
        field.set (object, value);
        field.setAccessible (false);
    }

}



