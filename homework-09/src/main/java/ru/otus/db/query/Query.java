package ru.otus.db.query;

public abstract class Query {
    protected final String table;
    protected final String queryTemplate;

    protected Query(String table, String queryTemplate) {
        this.table = table;
        this.queryTemplate = queryTemplate;
    }

    public abstract String build();
}
