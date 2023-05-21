package ru.nsu.ccfit.muratov.railroad.database.row.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubleColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setDouble(columnIndex, Double.parseDouble(value));
    }
}
