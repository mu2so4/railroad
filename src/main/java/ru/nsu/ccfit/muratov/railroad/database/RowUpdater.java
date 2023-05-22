package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RowUpdater {
    private final String queryTemplate;

    public RowUpdater() throws IOException {
        queryTemplate = QueryLoader.loadQuery("queries/main/update.sql");
    }

    public void updateRow(String tableName, Map<String, String> rowKey, Map<String, String> newValues)
            throws DatabaseException, SQLException, ProductCreatorException, IOException {
        Table table = Schema.getInstance().getTable(tableName);
        //todo check if full primary key

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
