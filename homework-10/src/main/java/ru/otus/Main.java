package ru.otus;

import ru.otus.db.dataset.AddressDataSet;
import ru.otus.db.dataset.PhoneDataSet;
import ru.otus.db.dataset.UserDataSet;
import ru.otus.db.service.DBService;
import ru.otus.db.service.DBServiceHibernateImpl;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();
        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        AddressDataSet address1 = new AddressDataSet("Elm Street");
        PhoneDataSet phone1 = new PhoneDataSet("+1 666 666-13-13");
        PhoneDataSet phone2 = new PhoneDataSet("+1 666 131-36-66");
        List<PhoneDataSet> phones1 = Arrays.asList(phone1, phone2);
        UserDataSet user1 = new UserDataSet(1, "Freddy", 42, address1, phones1);
        phone1.setUser(user1);
        phone2.setUser(user1);
        dbService.saveUser(user1);

        AddressDataSet address2 = new AddressDataSet("Camp Crystal Lake");
        PhoneDataSet phone3 = new PhoneDataSet("+1 313 666-13-13");
        PhoneDataSet phone4 = new PhoneDataSet("+1 313 131-36-66");
        List<PhoneDataSet> phones2 = Arrays.asList(phone3, phone4);
        UserDataSet user2 = new UserDataSet(2, "Jason", 37, address2, phones2);
        phone3.setUser(user2);
        phone4.setUser(user2);
        dbService.saveUser(user2);

        UserDataSet dataSet = dbService.getUser(1);
        System.out.println(dataSet);

        dataSet = dbService.getUserByName("Jason");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.getAllUsers();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();
    }
}
