package ru.otus.homework12;

import ru.otus.db.dataset.*;
import ru.otus.db.service.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DBServiceHibernateWithCacheImpl dbService = new DBServiceHibernateWithCacheImpl();
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
            Thread.sleep(1_000);
            Random random = new Random();
            UserDataSet user = dbService.getUser(random.nextInt(50) + 1);
            System.out.println("Get user by id: " + user);
            System.out.println("Cache hits: " + dbService.getCacheHitCount());
            System.out.println("Cache misses: " + dbService.getCacheMissCount());
        }
    }
}
