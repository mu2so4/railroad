package ru.nsu.ccfit.muratov.railroad.database.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Table {
    private String name;
    private TableType tableType;
    private boolean isMutable;
}
