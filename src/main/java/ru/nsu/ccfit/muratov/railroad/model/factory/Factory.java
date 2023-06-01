package ru.nsu.ccfit.muratov.railroad.model.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Factory {
    private static final Logger logger = Logger.getLogger(Factory.class.getCanonicalName());
    private final ProductCreator creator;

    public Factory(Class<?> basicClass, Map<String, Class<?>> producers, ProductCreator creator) {
        this.creator = creator;
        Map<String, Class<?>> productClasses = new HashMap<>();
        for(Map.Entry<String, Class<?>> entry: producers.entrySet()) {
            if(!basicClass.isAssignableFrom(entry.getValue())) {
                logger.severe(() -> String.format("'%s' is not a subclass of '%s'",
                        entry.getValue(), basicClass.getCanonicalName()));
            }
            else {
                productClasses.put(entry.getKey(), entry.getValue());
            }
        }
        creator.setProductClasses(productClasses);
    }

    public Object createProduct(String name, String[] args) throws ProductCreatorException {
        return creator.createProduct(name, args);
    }
}
