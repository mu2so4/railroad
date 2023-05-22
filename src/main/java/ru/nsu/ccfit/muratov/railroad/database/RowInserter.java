package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

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
        StringBuilder header = new StringBuilder();
        StringBuilder valuesPlace = new StringBuilder();
        for(Map.Entry<String, String> entry: values) {
            header.append(String.format(" \"%s\", ", entry.getKey()));
            valuesPlace.append("?, ");
        }
        header.deleteCharAt(header.length() - 2);
        valuesPlace.deleteCharAt(valuesPlace.length() - 2);
        String query = String.format(queryTemplate, tableName, header, valuesPlace);
        Table table = Schema.getInstance().getTable(tableName);

        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, values, table, 1);
            statement.execute();
        }
    }
}
