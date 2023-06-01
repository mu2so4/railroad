package ru.nsu.ccfit.muratov.railroad.model.column.form;

public class IntervalInputCast implements InputCast {
    @Override
    public String getCast() {
        return " ::interval ";
    }
}
