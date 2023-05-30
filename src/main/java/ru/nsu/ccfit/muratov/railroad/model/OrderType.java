package ru.nsu.ccfit.muratov.railroad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    ASC("ASC"),
    DESC("DESC");

    private final String displayName;
}
