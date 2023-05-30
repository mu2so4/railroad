package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.database.table.TableListReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Schema {
    private static Schema dbSchema;

    private final Map<String, Table> tables = new HashMap<>();

    private Schema() throws IOException, SQLException, DatabaseException {
        TableListReader tableListReader = new TableListReader();
        List<Table> list = tableListReader.loadTableList();
        for(Table table: list) {
            tables.put(table.getName(), table);
        }
    }

    public static Schema getInstance() {
        try {
            if (dbSchema == null) {
                dbSchema = new Schema();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dbSchema;
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public List<Table> getTableList() {
        List<Table> list = new ArrayList<>(tables.values());
        list.sort(Comparator.comparing(Table::getName));
        return list;
    }
}
