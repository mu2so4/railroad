package ru.nsu.ccfit.muratov.railroad.model.column;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataType {
    INT4("integer"),
    FLOAT8("double"),
    VARCHAR("varchar"),
    DATE("date"),
    BPCHAR("character"),
    MONEY("money"),
    INTERVAL("interval"),
    TEXT("text"),
    NUMERIC("numeric"),
    TIMESTAMP("timestamp"),
    TIMESTAMPTZ("timestamp_with_timezone"),
    BOOL("boolean");

    private final String displayName;
}
