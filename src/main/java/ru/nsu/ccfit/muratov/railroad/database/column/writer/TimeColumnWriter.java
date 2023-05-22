package ru.nsu.ccfit.muratov.railroad.database.column.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class TimeColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setTime(columnIndex, Time.valueOf(value));
    }
}
