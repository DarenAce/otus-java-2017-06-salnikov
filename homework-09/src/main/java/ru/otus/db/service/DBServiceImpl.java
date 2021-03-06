package ru.otus.db.service;

import ru.otus.db.dao.*;
import ru.otus.db.dataset.UserDataSet;
import ru.otus.util.ConnectionHelper;

import java.sql.Connection;

public class DBServiceImpl implements DBService{
    private final Connection connection;

    public DBServiceImpl() {
        this.connection = ConnectionHelper.getConnection();
    }

    @Override
    public void saveUser(UserDataSet user) {
        new UserDAO(connection).save(user);
    }

    @Override
    public UserDataSet getUser(long id) {
        return new UserDAO(connection).load(id);
    }
}
