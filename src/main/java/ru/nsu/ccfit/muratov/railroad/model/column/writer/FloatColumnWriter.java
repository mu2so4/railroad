package ru.nsu.ccfit.muratov.railroad.model.column.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FloatColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setFloat(columnIndex, Float.parseFloat(value));
    }
}
