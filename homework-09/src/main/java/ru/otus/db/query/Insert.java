package ru.otus.db.query;

import ru.otus.db.dataset.DataSet;

public class Insert extends ChangeQuery {
    public Insert(DataSet dataSet) {
        super(dataSet, "insert into ${table} (${names}) values (${values});");
    }

    @Override
    public String build() {
        addColumnsFromObject();
        String names = String.join(", ", columnNameToValue.keySet());
        String values = String.join(", ", columnNameToValue.values());
        return queryTemplate.replaceAll("\\$\\{table}", table)
                .replaceAll("\\$\\{names}", names)
                .replaceAll("\\$\\{values}", values);

    }
}
