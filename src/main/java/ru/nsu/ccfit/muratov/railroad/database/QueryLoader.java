package ru.nsu.ccfit.muratov.railroad.database;

import ru.nsu.ccfit.muratov.railroad.database.table.Table;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Arrays;

public class QueryLoader {
    private QueryLoader() {}

    public static String loadQuery(String filename) throws IOException {
        String query;
        try(InputStream input = QueryLoader.class.getClassLoader().getResourceAsStream(filename)) {
            if(input == null) {
                throw new FileNotFoundException(String.format("resource '%s' not found", filename));
            }
            byte[] bytes = input.readAllBytes();
            query = new String(bytes, StandardCharsets.UTF_8);
        }
        return query;
    }

    public static boolean checkPrimaryKey(Table table, Row key) {
        String[] givenKey = key.getValues().keySet().toArray(new String[0]);
        String[] trueKey = new String[table.getPrimaryKey().size()];
        for(int index = 0; index < trueKey.length; index++) {
            trueKey[index] = table.getPrimaryKey().get(index).getName();
        }
        Arrays.sort(givenKey);
        Arrays.sort(trueKey);
        return Arrays.equals(givenKey, trueKey);
    }
}
