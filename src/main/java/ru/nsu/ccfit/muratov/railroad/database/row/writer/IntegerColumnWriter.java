package ru.nsu.ccfit.muratov.railroad.database.row.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntegerColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setInt(columnIndex, Integer.parseInt(value));
    }
}
