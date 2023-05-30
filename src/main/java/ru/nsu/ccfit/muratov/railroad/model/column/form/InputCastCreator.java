package ru.nsu.ccfit.muratov.railroad.model.column.form;

import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InputCastCreator implements ProductCreator {
    private final Map<String, Object> products = new HashMap<>();
    private Object defaultProduct;

    @Override
    public Object createProduct(String productName, String[] args) {
        if(!products.containsKey(productName)) {
            return defaultProduct;
        }
        return products.get(productName);
    }

    @Override
    public void setProductClasses(Map<String, Class<?>> productList) {
        products.clear();
        for(Map.Entry<String, Class<?>> entry: productList.entrySet()) {
            try {
                Object object = entry.getValue().getDeclaredConstructor().newInstance();
                products.put(entry.getKey(), object);
                if(Objects.equals(entry.getKey(), "default")) {
                    defaultProduct = object;
                }
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                logger.severe(e::getMessage);
            }
        }
    }
}
