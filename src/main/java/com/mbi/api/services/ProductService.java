package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.ProductModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.repositories.ProductRepository;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.mbi.api.exceptions.ExceptionSupplier.*;

/**
 * Product service.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRunRepository testRunRepository;

    public ResponseEntity<CreatedResponse> createProduct(final ProductModel productModel)
            throws AlreadyExistsException {
        if (productRepository.findByName(productModel.getName()).isPresent()) {
            throw EXISTS_SUPPLIER.apply(ProductEntity.class, ALREADY_EXISTS_ERROR_MESSAGE).get();
        }

        final var productEntity = new ModelMapper().map(productModel, ProductEntity.class);
        productRepository.save(productEntity);
        final var createdModel = new ModelMapper().map(productEntity, CreatedResponse.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<ProductResponse> getProductByName(final String name) throws NotFoundException {
        final var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var productResponse = new ModelMapper().map(productEntity, ProductResponse.class);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    public ResponseEntity deleteProductByName(final String name, final Pageable pageable)
            throws NotFoundException, BadRequestException {
        final var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        if (testRunRepository.findAllByProduct(productEntity, pageable).isPresent()) {
            throw BAD_REQUEST_SUPPLIER.apply(ProductEntity.class,
                    "Can't remove product! Dependent test runs exist").get();
        }

        productRepository.delete(productEntity);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Page<ProductResponse>> getAllProducts(final Pageable pageable) {
        final var productsResponse = productRepository.findAll(pageable)
                .get()
                .map(p -> new ModelMapper().map(p, ProductResponse.class));

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }
}
