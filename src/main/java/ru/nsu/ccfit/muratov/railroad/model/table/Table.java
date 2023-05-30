package ru.nsu.ccfit.muratov.railroad.model.table;

import lombok.Getter;
import ru.nsu.ccfit.muratov.railroad.model.column.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class Table {
    private String name;
    private TableType tableType;
    private boolean isMutable;
    private final List<Column> columns;
    private final List<Column> primaryKey = new ArrayList<>();

    public Table(String name, TableType tableType, boolean isMutable, List<Column> columns, Short[] pk) {
        this.name = name;
        this.tableType = tableType;
        this.isMutable = isMutable;
        this.columns = columns;
        if(pk != null) {
            pkFind:
            for (short key : pk) {
                for (Column column : columns) {
                    if (column.getOrderNumber() == key) {
                        primaryKey.add(column);
                        continue pkFind;
                    }
                }
            }
        }
    }


    public Column getColumn(String columnName) {
        for(Column column: columns) {
            if(Objects.equals(column.getName(), columnName)) {
                return column;
            }
        }
        return null;
    }

    public boolean isPrimaryKey(String columnName) {
        for(Column column: columns) {
            if(column.getName().equals(columnName)) {
                return true;
            }
        }
        return false;
    }
}
