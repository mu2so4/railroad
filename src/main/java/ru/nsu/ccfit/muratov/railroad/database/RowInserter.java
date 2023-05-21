package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.column.Column;
import ru.nsu.ccfit.muratov.railroad.database.row.writer.ColumnWriter;
import ru.nsu.ccfit.muratov.railroad.database.table.Table;
import ru.nsu.ccfit.muratov.railroad.database.schema.Schema;
import ru.nsu.ccfit.muratov.railroad.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.factory.creator.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RowInserter {
    private final String queryTemplate;

    public RowInserter() throws IOException {
        queryTemplate = QueryLoader.loadQuery("queries/main/insert.sql");
    }

    public void insertRow(String tableName, Map<String, String> values)
            throws DatabaseException, SQLException, IOException, ProductCreatorException {
        StringBuilder header = new StringBuilder();
        StringBuilder valuesPlace = new StringBuilder();
        for(Map.Entry<String, String> entry: values.entrySet()) {
            header.append(String.format(" \"%s\", ", entry.getKey()));
            valuesPlace.append("?, ");
        }
        header.deleteCharAt(header.length() - 2);
        valuesPlace.deleteCharAt(valuesPlace.length() - 2);
        String query = String.format(queryTemplate, tableName, header, valuesPlace);
        Table table = Schema.getInstance().getTable(tableName);

        try(PreparedStatement statement = Database.getInstance().prepareStatement(query)) {
            List<Map.Entry<String, String>> list = values.entrySet().stream().toList();
            for(int index = 0; index < list.size(); index++) {
                Map.Entry<String, String> entry = list.get(index);
                Column column = table.getColumn(entry.getKey());
                String type = column.getDataType().getDisplayName();

                ColumnWriter writer = (ColumnWriter) AbstractFactory.instance().
                        getFactory("column_writer").createProduct(type, null);
                writer.write(statement, index + 1, entry.getValue());
            }
            statement.execute();
        }
    }
}
