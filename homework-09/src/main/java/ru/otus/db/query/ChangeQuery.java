package ru.otus.db.query;

import ru.otus.db.dataset.DataSet;
import ru.otus.util.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ChangeQuery extends Query {
    protected final DataSet dataSet;
    protected final Map<String, String> columnNameToValue;

    protected ChangeQuery(DataSet dataSet, String queryTemplate) {
        super(ReflectionHelper.getTableName(dataSet.getClass()), queryTemplate);
        this.dataSet = dataSet;
        this.columnNameToValue = new LinkedHashMap<>();
    }

    protected void addColumnsFromObject() {
        List<Field> fields = ReflectionHelper.getColumnFields(dataSet.getClass());
        for (Field field : fields) {
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                if (field.getType() == String.class) {
                    columnNameToValue.put(field.getName(), "'" + String.valueOf(field.get(dataSet)) + "'");
                } else{
                    columnNameToValue.put(field.getName(), String.valueOf(field.get(dataSet)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(isAccessible);
        }
    }
}
