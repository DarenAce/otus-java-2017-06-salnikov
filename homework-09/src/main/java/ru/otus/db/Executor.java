package ru.otus.db;

import ru.otus.db.dataset.*;
import ru.otus.db.handler.ResultHandler;
import ru.otus.db.query.*;
import ru.otus.util.*;

import javax.persistence.*;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Executor {
    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(query)
        ) {
            return handler.handle(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int execUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            return statement.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public <T extends DataSet> void save(T dataSet) {
        if (dataSet == null) {
            throw new IllegalArgumentException("Dataset is null");
        }
        Executor executor = new Executor(connection);
        DataSet loaded = load(dataSet.getId(), dataSet.getClass());
        if (loaded == null) {
            executor.execUpdate(new Insert(dataSet).build());
        } else {
            executor.execUpdate(new Update(dataSet, "id=" + dataSet.getId()).build());
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        T result;
        Executor executor = new Executor(connection);
        Select query = new Select(ReflectionHelper.getTableName(clazz), "id=" + id);
        result = executor.execQuery(query.build(), resultSet -> {
            T instance = null;
            if (resultSet.next()) {
                try {
                    instance = clazz.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                List<Field> fields = ReflectionHelper.getColumnFields(clazz);
                for (Field field : fields) {
                    Column column = field.getAnnotation(Column.class);
                    ReflectionHelper.setFieldValue(instance, field, resultSet.getObject(column.name()));
                }
            }
            return instance;
        });
        return result;
    }
}
