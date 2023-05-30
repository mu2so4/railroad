package ru.nsu.ccfit.muratov.railroad.database.table;

import ru.nsu.ccfit.muratov.railroad.database.*;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;
import ru.nsu.ccfit.muratov.railroad.database.column.ColumnListReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TableReader {
    private final String query;

    public TableReader() throws IOException {
        query = QueryLoader.loadQuery("/queries/main/table_rows.sql");
    }

    public void fetchTable(String tableName, OrderByList list) throws IOException, SQLException, DatabaseException {
        ColumnListReader columnListReader = new ColumnListReader();
        List<Column> columns = columnListReader.getTableColumns(tableName);

        Connection db = Database.getInstance();
        try(Statement statement = db.createStatement()) {
            String fullQuery = String.format(query, tableName);
            if(list != null) {
                fullQuery += list.makeOrderByStatement();
            }
            ResultSet set = statement.executeQuery(fullQuery);
            while(set.next()) {
                for (Column column : columns) {
                    System.out.printf("%s ", set.getString(column.getName()));
                }
                System.out.println();
            }
        }
    }
}
