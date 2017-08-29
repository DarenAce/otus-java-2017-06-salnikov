package ru.otus.db.dao;

import ru.otus.db.*;
import ru.otus.db.dataset.*;

import java.sql.*;

public class UserDAO {
    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    public void save(UserDataSet userDataSet) {
        Executor executor = new Executor(connection);
        executor.save(userDataSet);
    }

    public UserDataSet load(long id) {
        Executor executor = new Executor(connection);
        return executor.load(id, UserDataSet.class);
    }
}
