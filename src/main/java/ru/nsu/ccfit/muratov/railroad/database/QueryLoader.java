package ru.nsu.ccfit.muratov.railroad.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
}
