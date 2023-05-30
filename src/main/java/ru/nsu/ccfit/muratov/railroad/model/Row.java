package ru.nsu.ccfit.muratov.railroad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Row implements Iterable<Map.Entry<String, String>> {
    private final Map<String, String> values;

    public Row() {
        values = new HashMap<>();
    }

    public void add(String columnName, String value) {
        values.put(columnName, value);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return values.entrySet().iterator();
    }
}
