package ru.nsu.ccfit.muratov.railroad.model;

import ru.nsu.ccfit.muratov.railroad.model.column.Column;
import ru.nsu.ccfit.muratov.railroad.model.column.form.InputCast;
import ru.nsu.ccfit.muratov.railroad.model.column.writer.ColumnWriter;
import ru.nsu.ccfit.muratov.railroad.model.table.Table;
import ru.nsu.ccfit.muratov.railroad.model.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class QueryFormFiller {
    private QueryFormFiller() {}

    public static StringBuilder createSimpleForm(Row values, String suffix, String separator) {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, String> entry: values) {
            result.append(String.format(" \"%s\" %s %s ", entry.getKey(), suffix, separator));
        }
        result.delete(result.length() - separator.length() - 1, result.length() - 1);
        return result;
    }

    public static StringBuilder createCompoundForm(Row values, String suffix, String separator, Table table)
            throws IOException, ProductCreatorException {
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, String> entry: values) {
            String datatype = table.getColumn(entry.getKey()).getDataType().getDisplayName();
            InputCast caster = (InputCast) AbstractFactory.instance().
                    getFactory("query_input_cast").createProduct(datatype, null);
            result.append(
                    String.format(" \"%s\" %s %s %s ", entry.getKey(), suffix, caster.getCast(), separator));
        }
        result.delete(result.length() - separator.length() - 1, result.length() - 1);
        return result;
    }

    public static void fillForm(PreparedStatement statement, Row values, Table table, int startIndex)
            throws IOException, SQLException, ProductCreatorException {
        List<Map.Entry<String, String>> list = values.getValues().entrySet().stream().toList();
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
