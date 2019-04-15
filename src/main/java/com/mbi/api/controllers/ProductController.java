package com.mbi.api.controllers;

import com.mbi.api.exceptions.AlreadyExistsException;
import com.mbi.api.exceptions.BadRequestException;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.ProductModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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
        return productService.createProduct(productModel);
    }

    @RequestMapping(method = GET, path = "/products/{name}", produces = "application/json")
    public ResponseEntity<ProductResponse> getByName(@PathVariable("name") final String name)
            throws NotFoundException {
        return productService.getProductByName(name);
    }

    @RequestMapping(method = GET, path = "/products", produces = "application/json")
    public ResponseEntity<List<ProductResponse>> getAll() {
        return productService.getAllProducts();
    }

    @RequestMapping(method = DELETE, path = "/products/{name}", produces = "application/json")
    public ResponseEntity deleteByName(@PathVariable("name") final String name)
            throws NotFoundException, BadRequestException {
        return productService.deleteProductByName(name);
    }
}
