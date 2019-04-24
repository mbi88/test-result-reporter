package com.mbi.api.services;

import org.modelmapper.ModelMapper;

/**
 * Base service.
 */
public class BaseService {

    protected final ModelMapper mapper = new ModelMapper();
}
