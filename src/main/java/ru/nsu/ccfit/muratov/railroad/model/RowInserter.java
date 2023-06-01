package ru.nsu.ccfit.muratov.railroad.model;

import ru.nsu.ccfit.muratov.railroad.model.column.Column;
import ru.nsu.ccfit.muratov.railroad.model.column.form.InputCast;
import ru.nsu.ccfit.muratov.railroad.model.table.Table;
import ru.nsu.ccfit.muratov.railroad.model.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class RowInserter {
    private static final String queryTemplate =
        """
        INSERT INTO "%s"
        (%s)
        VALUES (%s)
        """;

    private RowInserter() {}

    public static Row insertRow(String tableName, Row values)
            throws DatabaseException, SQLException, IOException, ProductCreatorException {
        Table table = Schema.getInstance().getTable(tableName);
        Map<String, String> valuesMap = values.getValues();
        valuesMap.keySet().removeIf(
                (entry) -> valuesMap.get(entry).isEmpty() || valuesMap.get(entry) == null);
        if(values.getValues().isEmpty()) {
            throw new SQLException("not set parameters");
        }
        StringBuilder header = QueryFormFiller.createSimpleForm(values, "", ", ");
        StringBuilder valuesPlace = new StringBuilder();
        for(Map.Entry<String, String> column: values) {
            String datatype = table.getColumn(column.getKey()).getDataType().getDisplayName();
            InputCast caster = (InputCast) AbstractFactory.instance().
                    getFactory("query_input_cast").createProduct(datatype, null);
            valuesPlace.append(String.format("?%s, ", caster.getCast()));
        }
        valuesPlace.deleteCharAt(valuesPlace.length() - 2);
        String query = String.format(queryTemplate, tableName, header, valuesPlace);

        List<String> pks = new ArrayList<>();
        for(Column column: table.getPrimaryKey()) {
            pks.add(column.getName());
        }
        String[] pkNames = pks.toArray(new String[0]);

        Map<String, String> mapKey = new HashMap<>();
        try(PreparedStatement statement =
                    Database.getInstance().prepareStatement(query, pkNames)) {
            QueryFormFiller.fillForm(statement, values, table, 1);
            statement.execute();
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            for(Column column: table.getPrimaryKey()) {
                String columnName = column.getName();
                mapKey.put(columnName, keys.getString(columnName));
            }
        }
        return new Row(mapKey);
    }
}
