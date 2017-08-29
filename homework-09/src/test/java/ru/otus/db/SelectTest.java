package ru.otus.db;

import org.junit.*;
import ru.otus.db.query.*;

public class SelectTest {
    @Test
    public void buildWithoutClause() {
        Select select = new Select("test");
        Assert.assertEquals("select * from test;", select.build());
    }

    @Test
    public void buildWithClause() {
        Select select = new Select("test", "clause");
        Assert.assertEquals("select * from test where clause;", select.build());
    }
}
