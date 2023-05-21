package ru.nsu.ccfit.muratov.railroad.database.row.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BooleanColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setBoolean(columnIndex, Boolean.parseBoolean(value));
    }
}
