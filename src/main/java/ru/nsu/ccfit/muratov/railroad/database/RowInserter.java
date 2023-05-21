package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.row.Row;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RowInserter {
    private String queryTemplate;

    public RowInserter() throws IOException {
        queryTemplate = QueryLoader.loadQuery("queries/main/insert.sql");
    }

    public String insertRow(String tableName, Map<String, Object> values) {
        StringBuilder header = new StringBuilder();
        StringBuilder valuesPlace = new StringBuilder();
        for(Map.Entry<String, Object> entry: values.entrySet()) {
            header.append(String.format(" \"%s\", ", entry.getKey()));
            valuesPlace.append("?, ");
        }
        header.deleteCharAt(header.length() - 2);
        valuesPlace.deleteCharAt(valuesPlace.length() - 2);

        return String.format(queryTemplate, tableName, header, valuesPlace);

        /*try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            Map.Entry<String, Object>[] arr = values.entrySet().toArray();
            for(Map.Entry<String, Object> entry: values.entrySet()) {
                statement.setString(entry.getKey(), (String) entry.getValue());
            }
        }*/
    }
}
