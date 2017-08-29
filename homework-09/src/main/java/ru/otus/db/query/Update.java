package ru.otus.db.query;

import ru.otus.db.dataset.DataSet;

import java.util.ArrayList;
import java.util.List;

public class Update extends ChangeQuery {
    public Update(DataSet dataSet) {
        super(dataSet, "update ${table} set ${nameToValues};");
    }

    public Update(DataSet dataSet, String clause) {
        super(dataSet, "update ${table} set ${nameToValues} where " + clause + ";");
    }

    @Override
    public String build() {
        addColumnsFromObject();
        String nameToValues = String.join(", ", getNameToValueList());
        return queryTemplate.replaceAll("\\$\\{table}", table)
                .replaceAll("\\$\\{nameToValues}", nameToValues);

    }

    private List<String> getNameToValueList() {
        List<String> result = new ArrayList<>();
        columnNameToValue.forEach((name, value) -> result.add(name + "=" + value));
        return result;
    }
}
