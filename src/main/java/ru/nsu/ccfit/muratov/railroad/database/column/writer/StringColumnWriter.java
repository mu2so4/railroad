package ru.nsu.ccfit.muratov.railroad.database.column.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setString(columnIndex, value);
    }
}
