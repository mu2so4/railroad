package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RowUpdater {
    private final String queryTemplate;

    public RowUpdater() throws IOException {
        queryTemplate = QueryLoader.loadQuery("queries/main/update.sql");
    }

    public String updateRow(String tableName, Map<String, String> rowKey, Map<String, String> newValues) {
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
        return query;
    }
}
