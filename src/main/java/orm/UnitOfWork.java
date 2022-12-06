package orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class UnitOfWork<T extends Entity> {

    private final Connection conn;
    private final String tableName;

    private final List<T> newEntities = new ArrayList<> ();
    private final List<T> updateEntities = new ArrayList<> ();
    private final List<T> deleteEntities = new ArrayList<> ();

    public UnitOfWork (Connection conn, Class<T> clazz) {
        this.conn = conn;
        this.tableName = clazz.getAnnotation (TableName.class).value ();
    }

    public void registerNew (T entity) {
        this.newEntities.add (entity);
    }

    public void registerUpdate (T entity) {
        this.updateEntities.add (entity);
    }

    public void registerDelete (T entity) {
        this.deleteEntities.add (entity);
    }

    public void commit () {
        try {
            conn.setAutoCommit (false);
            insert ();
            update ();
            delete ();
            conn.commit ();
        } catch (Exception e) {
            e.printStackTrace ();
            try {
                conn.rollback ();
            } catch (SQLException ex) {
                ex.printStackTrace ();
            }
        }
    }

    private void insert () throws Exception {
        for (Entity entity : newEntities) {
            String sql = createSqlInsert (entity);
            executeUpdate (sql);
            System.out.println (sql);
        }
    }

    private void update () throws Exception {
        for (Entity entity : updateEntities) {
            String sql = createSqlUpdate (entity);
            executeUpdate (sql);
            System.out.println (sql);
        }
    }

    private void delete () throws Exception {
        for (Entity entity : deleteEntities) {
            String sql = createSqlDelete (entity);
            executeUpdate (sql);
            System.out.println (sql);
        }
    }

    private String createSqlInsert (Object entity) throws Exception {
        List<Field> fields = new ArrayList<> ();

        for (Field field : entity.getClass ().getDeclaredFields ()) {
            field.setAccessible (true);
            if (field.getAnnotation (PrimaryKey.class) == null
                    && field.getAnnotation (ColumnName.class) != null
                    && field.get (entity) != null) {
                fields.add (field);
            }
        }

        StringBuilder columnNames = new StringBuilder ();
        StringBuilder values = new StringBuilder ();

        for (int i = 0; i < fields.size (); i++) {
            String columnName = fields.get (i).getAnnotation (ColumnName.class).value ();
            Object value = fields.get (i).get (entity);

            columnNames.append ("`").append (columnName).append ("`");
            values.append ("'").append (value).append ("'");

            if (i < fields.size () - 1) {
                columnNames.append (", ");
                values.append (", ");
            }
            fields.get (i).setAccessible (false);
        }
        return String.format ("INSERT INTO `%s` (%s) VALUES (%s);",
                this.tableName, columnNames, values);
    }

    private String createSqlUpdate (Object entity) throws Exception {
        List<Field> fields = new ArrayList<> ();
        for (Field field : entity.getClass ().getDeclaredFields ()) {
            field.setAccessible (true);
            if (field.getAnnotation (ColumnName.class) != null
                    && field.get (entity) != null) {
                fields.add (field);
            }
        }

        StringBuilder values = new StringBuilder ();
        Object id = null;
        for (int i = 0; i < fields.size (); i++) {
            String columnName = fields.get (i).getAnnotation (ColumnName.class).value ();
            Object value = fields.get (i).get (entity);
            if (fields.get (i).getAnnotation (PrimaryKey.class) != null){
                id = value;
                continue;
            }
            values.append ("`").append (columnName).append ("`");
            values.append (" = ");
            values.append ("'").append (value).append ("'");
            fields.get (i).setAccessible (false);

            if (i < fields.size () - 1){
                values.append (", ");
            }
        }
        return String.format ("UPDATE `%s` SET %s WHERE id = '%s';", this.tableName, values, id);
    }

    private String createSqlDelete (Object entity) throws Exception {
        Object id = null;
        for (Field field : entity.getClass ().getDeclaredFields ()) {
            if (field.getAnnotation (PrimaryKey.class) != null) {
                field.setAccessible (true);
                id = field.get (entity);
                field.setAccessible (false);
                break;
            }
        }
        return String.format ("DELETE FROM `%s` WHERE id = '%s';", this.tableName, id);
    }

    private void executeUpdate (String sql) throws SQLException {
        Statement stat = this.conn.createStatement ();
        stat.executeUpdate (sql);
    }
}
