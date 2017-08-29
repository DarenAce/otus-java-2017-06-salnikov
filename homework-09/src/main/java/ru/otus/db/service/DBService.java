package ru.otus.db.service;

import ru.otus.db.dataset.UserDataSet;

public interface DBService {
    void saveUser(UserDataSet user);

    UserDataSet getUser(long id);
}
