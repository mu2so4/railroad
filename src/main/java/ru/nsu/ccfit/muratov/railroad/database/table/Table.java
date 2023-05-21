package ru.nsu.ccfit.muratov.railroad.database.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Table {
    private String name;
    private TableType tableType;
    private boolean isMutable;

    private final List<Column> columns;

    public Column getColumn(String columnName) {
        for(Column column: columns) {
            if(Objects.equals(column.getName(), columnName)) {
                return column;
            }
        }
        return null;
    }
}
