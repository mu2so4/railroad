package ru.nsu.ccfit.muratov.railroad.database.row.writer;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ColumnWriter {
    void write(PreparedStatement statement, int columnIndex, String value) throws SQLException;
}
