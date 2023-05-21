package ru.nsu.ccfit.muratov.railroad.database.table;

import ru.nsu.ccfit.muratov.railroad.database.Database;
import ru.nsu.ccfit.muratov.railroad.database.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.database.QueryLoader;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;
import ru.nsu.ccfit.muratov.railroad.database.column.ColumnListReader;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableListReader {
    private final String query;

    public TableListReader() throws IOException {
        query = QueryLoader.loadQuery("queries/meta/tables.sql");
    }

    public List<Table> loadTableList() throws SQLException, DatabaseException, IOException {
        ResultSet set;
        ArrayList<Table> list = new ArrayList<>();
        ColumnListReader columnListReader = new ColumnListReader();
        try(Statement statement = Database.getInstance().createStatement()) {
            set = statement.executeQuery(query);
            while(set.next()) {
                String tableName = set.getString("table_name");
                String typeName = set.getString("table_type");
                TableType type = TableType.valueOf(typeName.replace(" ", "_"));
                boolean isMutable = set.getBoolean("is_insertable_into");
                List<Column> columns = columnListReader.getTableColumns(tableName);

                list.add(new Table(tableName, type, isMutable, columns));
            }
        }
        return list;
    }
}
