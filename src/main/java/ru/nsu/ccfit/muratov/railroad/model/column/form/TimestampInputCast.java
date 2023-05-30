package ru.nsu.ccfit.muratov.railroad.model.column.form;

public class TimestampInputCast implements InputCast {
    @Override
    public String getCast() {
        return "::timestamp";
    }
}
