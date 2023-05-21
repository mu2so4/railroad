package ru.nsu.ccfit.muratov.railroad.database.table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.nsu.ccfit.muratov.railroad.database.column.Column;

import java.util.List;

@Getter
@AllArgsConstructor
public class Table {
    private String name;
    private TableType tableType;
    private boolean isMutable;

    private final List<Column> columns;
}
