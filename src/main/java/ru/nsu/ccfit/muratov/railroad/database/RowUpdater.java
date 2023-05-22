package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RowUpdater {
    private static final String queryTemplate =
        """
        UPDATE "%s"
        SET %s
        WHERE %s
        """;

    private RowUpdater() {}

    public static void updateRow(String tableName, Row rowKey, Row newValues)
            throws DatabaseException, SQLException, ProductCreatorException, IOException {
        Table table = Schema.getInstance().getTable(tableName);
        if(!QueryLoader.checkPrimaryKey(table, rowKey)) {
            throw new SQLException(
                    String.format("key not match to the primary key of table \"%s\"", table.getName()));
        }

        StringBuilder body = QueryFormFiller.createCompoundForm(newValues, "= ? ", ",", table);
        StringBuilder keyCheck = QueryFormFiller.createCompoundForm(rowKey, "= ?", " AND ", table);
        String query = String.format(queryTemplate, tableName, body, keyCheck);

        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, newValues, table, 1);
            QueryFormFiller.fillForm(statement, rowKey, table, newValues.getValues().size() + 1);
            statement.execute();
        }
    }
}
