package ru.otus.homework12;

import ru.otus.db.dataset.*;
import ru.otus.db.service.*;

import java.util.*;

public class DBWorker extends Thread {
    private DBService dbService;

    public DBWorker(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            List<PhoneDataSet> phones;
            if (i < 10) {
                phones = Arrays.asList(new PhoneDataSet("+7 777-77-7" + i), new PhoneDataSet("+1 111-11-1" + i));
            } else {
                phones = Arrays.asList(new PhoneDataSet("+7 777-77-" + i), new PhoneDataSet("+1 111-11-" + i));
            }
            UserDataSet user = new UserDataSet(i, "user " + i, (20 + i), new AddressDataSet("street " + i), phones);
            phones.forEach(phone -> phone.setUser(user));
            dbService.saveUser(user);
        }

        while (true) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            UserDataSet user = dbService.getUser(random.nextInt(50) + 1);
        }
    }
}
