package ru.nsu.ccfit.muratov.railroad.factory.creator;

import java.util.Map;

public interface ProductCreator {
    Object createProduct(Map<String, Class<?>> productList, String productName, String[] args)
            throws ProductCreatorException;
}
