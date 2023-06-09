package ru.nsu.ccfit.muratov.railroad.model.table;

import ru.nsu.ccfit.muratov.railroad.model.Database;
import ru.nsu.ccfit.muratov.railroad.model.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.model.QueryLoader;
import ru.nsu.ccfit.muratov.railroad.model.column.Column;
import ru.nsu.ccfit.muratov.railroad.model.column.ColumnListReader;

import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TableListReader {
    private final String query;

    public TableListReader() throws IOException {
        query = QueryLoader.loadQuery("/queries/meta/tables.sql");
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
                Array primaryKey = set.getArray("primary_key");
                Short[] keyArray;
                if(primaryKey != null) {
                    keyArray = (Short[]) primaryKey.getArray();
                }
                else {
                    keyArray = null;
                }

                list.add(new Table(tableName, type, isMutable, columns, keyArray));
            }
        }
        return list;
    }
}
