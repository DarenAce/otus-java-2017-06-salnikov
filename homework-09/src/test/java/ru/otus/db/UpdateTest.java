package ru.otus.db;

import org.junit.*;
import ru.otus.db.dataset.*;
import ru.otus.db.query.*;

public class UpdateTest {
    @Test
    public void buildWithoutClause() {
        UserDataSet userDataSet = new UserDataSet(1, "test", 42);
        Update update = new Update(userDataSet);
        Assert.assertEquals("update users set id=1, name='test', age=42;", update.build());
    }

    @Test
    public void buildWithClause() {
        UserDataSet userDataSet = new UserDataSet(1, "test", 42);
        Update update = new Update(userDataSet, "clause");
        Assert.assertEquals("update users set id=1, name='test', age=42 where clause;", update.build());
    }
}
