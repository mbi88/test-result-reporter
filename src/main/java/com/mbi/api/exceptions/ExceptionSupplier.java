package com.mbi.api.exceptions;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ExceptionSupplier {

    public static final String ALREADY_EXISTS_ERROR_MESSAGE = "Already exists";
    public static final String NOT_FOUND_ERROR_MESSAGE = "Not found";

    public static final BiFunction<Class, String, Supplier<NotFoundException>> NOT_FOUND_SUPPLIER = (entity, message) ->
            () -> new NotFoundException(entity, message);

    public static final BiFunction<Class, String, Supplier<BadRequestException>> BAD_REQUEST_SUPPLIER = (entity, message) ->
            () -> new BadRequestException(entity, message);

    public static final BiFunction<Class, String, Supplier<AlreadyExistsException>> EXISTS_SUPPLIER = (entity, message) ->
            () -> new AlreadyExistsException(entity, message);
}
