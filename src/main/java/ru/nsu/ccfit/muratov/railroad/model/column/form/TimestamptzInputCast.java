package ru.nsu.ccfit.muratov.railroad.model.column.form;

public class TimestamptzInputCast implements InputCast {
    @Override
    public String getCast() {
        return "::timestamptz";
    }
}
