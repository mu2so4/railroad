package ru.nsu.ccfit.muratov.railroad.model.query;

import ru.nsu.ccfit.muratov.railroad.model.Database;
import ru.nsu.ccfit.muratov.railroad.model.DatabaseException;
import ru.nsu.ccfit.muratov.railroad.model.Row;
import ru.nsu.ccfit.muratov.railroad.model.column.writer.ColumnWriter;
import ru.nsu.ccfit.muratov.railroad.model.factory.AbstractFactory;
import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreatorException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryExecutor {
    private final List<ColumnWriter> writerList = new ArrayList<>();
    private final String queryTemplate;

    public QueryExecutor(List<String> paramDataTypes, String queryTemplate) throws IOException, ProductCreatorException {
        for(var type: paramDataTypes) {
            ColumnWriter writer = (ColumnWriter) AbstractFactory.instance().
                    getFactory("column_writer").createProduct(type, null);
            writerList.add(writer);
        }
        this.queryTemplate = queryTemplate;
    }

    public List<Row> executeQuery(List<String> params) throws DatabaseException, SQLException {
        if(params.size() != writerList.size()) {
            throw new SQLException("param count is not the same");
        }
        List<Row> rows = new ArrayList<>();
        Connection db = Database.getInstance();
        try(PreparedStatement statement = db.prepareStatement(queryTemplate)) {
            for(int index = 0; index < writerList.size(); index++) {
                writerList.get(index).write(statement, index + 1, params.get(index));
            }
            ResultSet set = statement.executeQuery();
            ResultSetMetaData metaData = set.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columns = new String[columnCount];
            for(int index = 0; index < columnCount; index++) {
                columns[index] = metaData.getColumnLabel(index + 1);
            }
            while(set.next()) {
                Row row = new Row();
                for (String columnName : columns) {
                    row.add(columnName, set.getString(columnName));
                }
                rows.add(row);
            }
        }
        return rows;
    }
}
