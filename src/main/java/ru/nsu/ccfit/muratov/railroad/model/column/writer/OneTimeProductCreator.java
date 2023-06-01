package ru.nsu.ccfit.muratov.railroad.model.column.writer;

import ru.nsu.ccfit.muratov.railroad.model.factory.ProductCreator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class OneTimeProductCreator implements ProductCreator {
    private final Map<String, Object> products = new HashMap<>();

    @Override
    public Object createProduct(String productName, String[] args) {
        if (!products.containsKey(productName)) {
            logger.warning(() -> String.format("product name '%s' not found", productName));
            return null;
        }
        return products.get(productName);
    }

    @Override
    public void setProductClasses(Map<String, Class<?>> productList) {
        products.clear();
        for(Map.Entry<String, Class<?>> entry : productList.entrySet()) {
            try {
                products.put(entry.getKey(),
                        entry.getValue().getDeclaredConstructor().newInstance());
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                     NoSuchMethodException e) {
                logger.severe(e::getMessage);
            }
        }
    }
}
