package ru.nsu.ccfit.muratov.railroad.database.column.form;

public class MoneyInputCast implements InputCast {
    @Override
    public String getCast() {
        return " ::numeric::money ";
    }
}
