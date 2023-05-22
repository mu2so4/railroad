package ru.nsu.ccfit.muratov.railroad.database.column.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TimestampColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setTimestamp(columnIndex, Timestamp.valueOf(value));
    }
}
