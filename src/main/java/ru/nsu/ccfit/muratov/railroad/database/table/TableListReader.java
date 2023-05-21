package ru.nsu.ccfit.muratov.railroad.database.table;

import ru.nsu.ccfit.muratov.railroad.database.Database;
import ru.nsu.ccfit.muratov.railroad.database.QueryLoader;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableListReader {
    private final String query;

    public TableListReader() throws IOException {
        query = QueryLoader.loadQuery("queries/meta/fetch_tables.sql");
    }

    public List<Table> loadTableList() throws SQLException, IOException, ClassNotFoundException {
        ResultSet set;
        ArrayList<Table> list = new ArrayList<>();
        try(Statement statement = Database.getInstance().createStatement()) {
            set = statement.executeQuery(query);
            while(set.next()) {
                String tableName = set.getString("table_name");
                String typeName = set.getString("table_type");
                TableType type = TableType.valueOf(typeName.replace(" ", "_"));
                boolean isMutable = set.getBoolean("is_insertable_into");
                list.add(new Table(tableName, type, isMutable));
            }
        }
        return list;
    }
}
