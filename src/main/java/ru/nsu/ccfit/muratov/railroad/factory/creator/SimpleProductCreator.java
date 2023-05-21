package ru.nsu.ccfit.muratov.railroad.factory.creator;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SimpleProductCreator implements ProductCreator {
    @Override
    public Object createProduct(Map<String, Class<?>> productList, String productName, String[] args)
            throws ProductCreatorException {
        if (!productList.containsKey(productName)) {
            logger.warning(() -> String.format("product name '%s' not found", productName));
            return null;
        }
        try {
            return productList.get(productName).getDeclaredConstructor().newInstance();
        }
        catch(NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ProductCreatorException(e);
        }
    }
}
