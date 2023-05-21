package ru.nsu.ccfit.muratov.railroad.database.column;

import ru.nsu.ccfit.muratov.railroad.database.Database;
import ru.nsu.ccfit.muratov.railroad.database.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.database.QueryLoader;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColumnListReader {
    private final String query;

    public ColumnListReader() throws IOException {
        query = QueryLoader.loadQuery("queries/meta/fetch_table_rows.sql");
    }

    public List<Column> getTableColumns(String tableName) throws DatabaseException, SQLException {
        ResultSet set;
        ArrayList<Column> list = new ArrayList<>();
        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            statement.setString(1, tableName);
            set = statement.executeQuery();
            while(set.next()) {
                String columnName = set.getString("column_name");
                boolean isNullable = set.getBoolean("is_nullable");
                String typeName = set.getString("data_type");
                int maxCharLength = set.getInt("character_maximum_length");
                boolean isUpdatable = set.getBoolean("is_updatable");
                list.add(new Column(columnName, isNullable, typeName, maxCharLength, isUpdatable));
            }
        }
        return list;
    }
}
