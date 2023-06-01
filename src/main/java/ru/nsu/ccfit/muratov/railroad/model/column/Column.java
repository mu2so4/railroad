package ru.nsu.ccfit.muratov.railroad.model.column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Column {
    private String name;
    private boolean nullable;
    private DataType dataType;
    private int maxCharLength;
    private boolean updatable;
    private int orderNumber;
}
