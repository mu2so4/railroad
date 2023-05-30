package ru.nsu.ccfit.muratov.railroad.model;

import ru.nsu.ccfit.muratov.railroad.model.column.form.InputCast;
import ru.nsu.ccfit.muratov.railroad.model.table.Table;
import ru.nsu.ccfit.muratov.railroad.model.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class RowInserter {
    private static final String queryTemplate =
        """
        INSERT INTO "%s"
        (%s)
        VALUES (%s)
        """;

    private RowInserter() {}

    public static void insertRow(String tableName, Row values)
            throws DatabaseException, SQLException, IOException, ProductCreatorException {
        Table table = Schema.getInstance().getTable(tableName);
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

        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, values, table, 1);
            statement.execute();
        }
    }
}
