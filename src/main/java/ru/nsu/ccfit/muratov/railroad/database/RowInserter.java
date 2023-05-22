package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        StringBuilder header = QueryFormFiller.createForm(values, "", ", ");
        StringBuilder valuesPlace = new StringBuilder();
        valuesPlace.append("?, ".repeat(values.getValues().size()));
        valuesPlace.deleteCharAt(valuesPlace.length() - 2);
        String query = String.format(queryTemplate, tableName, header, valuesPlace);

        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, values, table, 1);
            statement.execute();
        }
    }
}
