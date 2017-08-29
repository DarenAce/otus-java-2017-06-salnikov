package ru.otus.db.query;

public class Select extends Query {
    public Select(String table) {
        super(table, "select * from ${table};");
    }

    public Select(String table, String clause) {
        super(table, "select * from ${table} where " + clause + ";");
    }

    @Override
    public String build() {
        return queryTemplate.replaceAll("\\$\\{table}", table);
    }
}
