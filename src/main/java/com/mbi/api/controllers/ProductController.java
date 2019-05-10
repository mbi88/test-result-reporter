package com.mbi.api.controllers;

import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.ProductModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Product controller.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(method = POST, path = "/products", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreatedResponse> create(@Valid @RequestBody final ProductModel productModel)
            throws AlreadyExistsException {
        return new ResponseEntity<>(productService.createProduct(productModel), HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, path = "/products/{name}", produces = "application/json")
    public ResponseEntity<ProductResponse> getByName(@PathVariable("name") final String name) throws NotFoundException {
        return new ResponseEntity<>(productService.getProductByName(name), HttpStatus.OK);
    }

    @RequestMapping(method = GET, path = "/products", produces = "application/json")
    public ResponseEntity<Page<ProductResponse>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size) {
        return new ResponseEntity<>(productService.getAllProducts(PageRequest.of(page, size)), HttpStatus.OK);
    }

    @RequestMapping(method = DELETE, path = "/products/{name}", produces = "application/json")
    public ResponseEntity deleteByName(
            @PathVariable("name") final String name,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size)
            throws NotFoundException, BadRequestException {
        productService.deleteProductByName(name, PageRequest.of(page, size));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
