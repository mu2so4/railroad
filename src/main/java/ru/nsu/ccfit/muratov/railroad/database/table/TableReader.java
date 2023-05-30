package ru.nsu.ccfit.muratov.railroad.database.table;

import ru.nsu.ccfit.muratov.railroad.database.*;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableReader {
    private final String query;
    private final Table table;

    public TableReader(String tableName) throws IOException {
        query = QueryLoader.loadQuery("/queries/main/table_rows.sql");
        table = Schema.getInstance().getTable(tableName);
    }

    public List<Row> fetchTable(OrderByList list) throws SQLException, DatabaseException {
        List<Column> columns = table.getColumns();

        Connection db = Database.getInstance();
        List<Row> rows = new ArrayList<>();
        try(Statement statement = db.createStatement()) {
            String fullQuery = String.format(query, table.getName());
            if(list != null) {
                fullQuery += list.makeOrderByStatement();
            }
            ResultSet set = statement.executeQuery(fullQuery);
            while(set.next()) {
                Row row = new Row();
                for (Column column : columns) {
                    String columnName = column.getName();
                    row.add(columnName, set.getString(columnName));
                }
            }
        }
        return rows;
    }
}
