package com.mbi.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;

import java.io.IOException;

/**
 * Base service.
 */
public class BaseService {

    protected final ModelMapper mapper = new ModelMapper();

    protected String objectToString(final Object object) throws JsonProcessingException {
        final var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(object);
    }

    protected <T> T stringToObject(final String string, final Class<T> clazz) throws IOException {
        return new ObjectMapper().readValue(string, clazz);
    }
}
