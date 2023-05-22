package ru.nsu.ccfit.muratov.railroad.factory.creator;

import java.util.Map;
import java.util.logging.Logger;

public interface ProductCreator {
    Logger logger = Logger.getLogger(ProductCreator.class.getCanonicalName());
    Object createProduct(String productName, String[] args)
            throws ProductCreatorException;

    void setProductClasses(Map<String, Class<?>> productList);
}
