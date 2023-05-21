package ru.nsu.ccfit.muratov.railroad.factory.creator;

import java.util.Map;
import java.util.logging.Logger;

public interface ProductCreator {
    Logger logger = Logger.getLogger(ProductCreator.class.getCanonicalName());
    Object createProduct(Map<String, Class<?>> productList, String productName, String[] args)
            throws ProductCreatorException;
}
