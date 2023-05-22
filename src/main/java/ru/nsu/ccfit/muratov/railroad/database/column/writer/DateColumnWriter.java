package ru.nsu.ccfit.muratov.railroad.database.column.writer;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DateColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setDate(columnIndex, Date.valueOf(value));
    }
}
