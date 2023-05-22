package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.column.Column;
import ru.nsu.ccfit.muratov.railroad.database.column.writer.ColumnWriter;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryFormFiller {
    private QueryFormFiller() {}

    public static void fillForm(PreparedStatement statement, Map<String, String> values, Table table, int startIndex)
            throws IOException, SQLException, ProductCreatorException {
        List<Map.Entry<String, String>> list = values.entrySet().stream().toList();
        for(int index = 0; index < list.size(); index++) {
            Map.Entry<String, String> entry = list.get(index);
            Column column = table.getColumn(entry.getKey());
            String type = column.getDataType().getDisplayName();

            ColumnWriter writer = (ColumnWriter) AbstractFactory.instance().
                    getFactory("column_writer").createProduct(type, null);
            writer.write(statement, index + startIndex, entry.getValue());
        }
    }
}
