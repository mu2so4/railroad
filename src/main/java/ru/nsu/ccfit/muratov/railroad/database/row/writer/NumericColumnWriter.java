package ru.nsu.ccfit.muratov.railroad.database.row.writer;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NumericColumnWriter implements ColumnWriter {
    @Override
    public void write(PreparedStatement statement, int columnIndex, String value) throws SQLException {
        statement.setBigDecimal(columnIndex, new BigDecimal(value));
    }
}
