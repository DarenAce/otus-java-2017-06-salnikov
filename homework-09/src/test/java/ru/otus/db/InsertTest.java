package ru.otus.db;

import org.junit.*;
import ru.otus.db.dataset.*;
import ru.otus.db.query.*;

public class InsertTest {
    @Test
    public void build() {
        UserDataSet userDataSet = new UserDataSet(1, "test", 42);
        Insert insert = new Insert(userDataSet);
        Assert.assertEquals("insert into users (id, name, age) values (1, 'test', 42);", insert.build());
    }
}
