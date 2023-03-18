package com.example.HRplatform.exceptions;

public class EntityExistsException extends ApiException {

    public EntityExistsException(Object id, Class<?> entityClass) {
        super(String.format("%s with id %s already exists!", entityClass.getSimpleName(), id.toString()));
    }

    public EntityExistsException(Object propertyValue, String propertyName, Class<?> entityClass) {
        super(String.format(
                "%s with %s %s already exists!",
                entityClass.getSimpleName(),
                propertyName,
                propertyValue.toString()));
    }

    public EntityExistsException(String message) {
        super(message);
    }

}
