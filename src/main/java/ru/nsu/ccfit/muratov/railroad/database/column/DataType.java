package ru.nsu.ccfit.muratov.railroad.database.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {
    INT4("integer"),
    VARCHAR("varchar"),
    DATE("date"),
    BPCHAR("character"),
    MONEY("money"),
    NUMERIC("numeric");

    private final String displayName;
}
