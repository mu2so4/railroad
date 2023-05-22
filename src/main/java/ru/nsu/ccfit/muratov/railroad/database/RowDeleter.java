package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RowDeleter {
    private static final String queryTemplate =
            """
            DELETE FROM "%s"
            WHERE %s
            """;

    public static void deleteRow(String tableName, Row key) throws SQLException,
            IOException, DatabaseException, ProductCreatorException {
        Table table = Schema.getInstance().getTable(tableName);
        if(!QueryLoader.checkPrimaryKey(table, key)) {
            throw new SQLException(
                    String.format("key not match to the primary key of table \"%s\"", table.getName()));
        }
        StringBuilder keyCheck = QueryFormFiller.createCompoundForm(key, "= ?", " AND ", table);
        String query = String.format(queryTemplate, tableName, keyCheck);
        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, key, table, 1);
            statement.execute();
        }
    }
}
