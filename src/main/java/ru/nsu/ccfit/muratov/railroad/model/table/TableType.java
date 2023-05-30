package ru.nsu.ccfit.muratov.railroad.model.table;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableType {
    BASE_TABLE("TABLE"),
    VIEW("VIEW");

    private final String displayName;
}
