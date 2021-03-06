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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.mbi.api.exceptions.ExceptionSupplier.*;

/**
 * Product service.
 */
@Service
public class ProductService extends BaseService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestRunRepository testRunRepository;

    public CreatedResponse createProduct(final ProductModel productModel) throws AlreadyExistsException {
        if (productRepository.findByName(productModel.getName()).isPresent()) {
            throw EXISTS_SUPPLIER.apply(ProductEntity.class, ALREADY_EXISTS_ERROR_MESSAGE).get();
        }

        final var productEntity = mapper.map(productModel, ProductEntity.class);
        productRepository.save(productEntity);

        return mapper.map(productEntity, CreatedResponse.class);
    }

    public ProductResponse getProductByName(final String name) throws NotFoundException {
        final var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        return mapper.map(productEntity, ProductResponse.class);
    }

    public void deleteProductByName(final String name, final Pageable pageable) throws NotFoundException,
            BadRequestException {
        final var productEntity = productRepository.findByName(name)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testRuns = testRunRepository.findAllByProduct(productEntity, pageable);
        if (testRuns.isPresent() && !testRuns.get().getContent().isEmpty()) {
            throw BAD_REQUEST_SUPPLIER.apply(ProductEntity.class,
                    "Can't remove product! Dependent test-runs exist").get();
        }

        productRepository.delete(productEntity);
    }

    public Page<ProductResponse> getAllProducts(final Pageable pageable) {
        return productRepository
                .findAll(pageable).get()
                .map(p -> mapper.map(p, ProductResponse.class));
    }
}
