package ru.nsu.ccfit.muratov.railroad.database.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {
    INT4("integer"),
    FLOAT8("float"),
    VARCHAR("varchar"),
    DATE("date"),
    BPCHAR("character"),
    MONEY("money"),
    INTERVAL("interval"),
    TEXT("text"),
    NUMERIC("numeric"),
    TIMESTAMP("timestamp"),
    TIMESTAMPTZ("timestamp with timezone"),
    BOOL("boolean");

    private final String displayName;
}
