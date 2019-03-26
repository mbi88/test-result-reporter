package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.ProductModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<CreatedModel> createProduct(final ProductModel productModel) throws AlreadyExistsException {
        var productEntity = new ModelMapper().map(productModel, ProductEntity.class);

        if (productRepository.findByName(productModel.getName()).isPresent()) {
            throw new AlreadyExistsException(ProductEntity.class);
        }

        productRepository.save(productEntity);

        var createdModel = new ModelMapper().map(productEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<ProductResponse> getProductByName(String name) throws NotFoundException {
        if (productRepository.findByName(name).isEmpty()) {
            throw new NotFoundException(ProductEntity.class);
        }

        var productEntity = productRepository.findByName(name).get();

        var productResponse = new ModelMapper().map(productEntity, ProductResponse.class);

        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    public ResponseEntity deleteProductByName(String name) throws NotFoundException {
        if (productRepository.findByName(name).isEmpty()) {
            throw new NotFoundException(ProductEntity.class);
        }

        var productEntity = productRepository.findByName(name).get();

        productRepository.delete(productEntity);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
