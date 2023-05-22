package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

public class RowUpdater {
    private static final String queryTemplate =
        """
        UPDATE "%s"
        SET %s
        WHERE %s
        """;

    private RowUpdater() {}

    public static void updateRow(String tableName, Map<String, String> rowKey, Map<String, String> newValues)
            throws DatabaseException, SQLException, ProductCreatorException, IOException {
        Table table = Schema.getInstance().getTable(tableName);
        String[] givenKey = rowKey.keySet().toArray(new String[0]);
        String[] trueKey = new String[table.getPrimaryKey().size()];
        for(int index = 0; index < trueKey.length; index++) {
            trueKey[index] = table.getPrimaryKey().get(index).getName();
        }
        Arrays.sort(givenKey);
        Arrays.sort(trueKey);
        if(!Arrays.equals(givenKey, trueKey)) {
            throw new SQLException(
                    String.format("key not match to the primary key of table \"%s\"", table.getName()));
        }

        StringBuilder body = new StringBuilder();
        StringBuilder keyCheck = new StringBuilder();
        for(Map.Entry<String, String> entry: newValues.entrySet()) {
            body.append(String.format(" \"%s\" = ?, ", entry.getKey()));
        }
        body.deleteCharAt(body.length() - 2);

        for(Map.Entry<String, String> entry: rowKey.entrySet()) {
            keyCheck.append(String.format(" \"%s\" = ? AND ", entry.getKey()));
        }
        keyCheck.delete(keyCheck.length() - 4, keyCheck.length() - 1);

        String query = String.format(queryTemplate, tableName, body, keyCheck);
        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            QueryFormFiller.fillForm(statement, newValues, table, 1);
            QueryFormFiller.fillForm(statement, rowKey, table, newValues.size() + 1);
            statement.execute();
        }
    }
}
