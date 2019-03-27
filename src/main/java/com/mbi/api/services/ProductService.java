package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.ProductModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.repositories.ProductRepository;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.mbi.api.exceptions.ExceptionSupplier.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRunRepository testRunRepository;

    public ResponseEntity<CreatedModel> createProduct(final ProductModel productModel) throws AlreadyExistsException {
        if (productRepository.findByName(productModel.getName()).isPresent()) {
            throw EXISTS_SUPPLIER.apply(ProductEntity.class, ALREADY_EXISTS_ERROR_MESSAGE).get();
        }

        var productEntity = new ModelMapper().map(productModel, ProductEntity.class);
        productRepository.save(productEntity);
        var createdModel = new ModelMapper().map(productEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<ProductResponse> getProductByName(final String name) throws NotFoundException {
        var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));
        var productResponse = new ModelMapper().map(productEntity, ProductResponse.class);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    public ResponseEntity deleteProductByName(final String name) throws NotFoundException, BadRequestException {
        var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        if (testRunRepository.findAllByProduct(productEntity).isPresent()) {
            throw BAD_REQUEST_SUPPLIER.apply(ProductEntity.class,
                    "Can't remove product! Dependent test runs exist").get();
        }

        productRepository.delete(productEntity);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        var productsResponse = ((List<ProductEntity>) productRepository.findAll())
                .stream()
                .map(p -> new ModelMapper().map(p, ProductResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }
}
