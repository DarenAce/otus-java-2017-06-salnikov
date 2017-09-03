package ru.otus;

import ru.otus.db.dataset.*;
import ru.otus.db.service.*;
import ru.otus.util.ConnectionHelper;
import ru.otus.db.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Connection connection = ConnectionHelper.getConnection();
        DBService dbService = new DBServiceImpl();
        Executor executor = new Executor(connection);
        try {
            System.out.println("Connected to: " + connection.getMetaData().getURL());
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());

            executor.execUpdate("create table users (id bigint auto_increment, name varchar(256), age int(3), primary key (id));");
            System.out.println("Table was created.");

            List<UserDataSet> users = new ArrayList<>();
            for (int i = 1; i < 6; i++) {
                users.add(new UserDataSet(i, "user " + (6 - i), 18 + i));
            }

            users.forEach(dbService::saveUser);
            System.out.println("5 users were added to database.");

            UserDataSet user = dbService.getUser(2);
            System.out.println("Get user with id = 2 from database: " + user);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                executor.execUpdate("drop table if exists users;");
                System.out.println("Table was deleted.");
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            System.out.println("Done!");
        }
    }
}
