package com.example.HRplatform.exceptions;

public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(Object id, Class<?> entityClass) {
        super(String.format("%s with id %s not found!", entityClass.getSimpleName(), id.toString()));
    }

    public EntityNotFoundException(Object propertyValue, String propertyName, Class<?> entityClass) {
        super(String.format(
                "%s with %s %s not found!",
                entityClass.getSimpleName(),
                propertyName,
                propertyValue.toString()));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
