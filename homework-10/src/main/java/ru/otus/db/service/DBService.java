package ru.otus.db.service;

import ru.otus.db.dataset.UserDataSet;

import java.util.*;

public interface DBService {
    String getLocalStatus();

    void saveUser(UserDataSet dataSet);

    UserDataSet getUser(long id);

    UserDataSet getUserByName(String name);

    List<UserDataSet> getAllUsers();

    void shutdown();
}
