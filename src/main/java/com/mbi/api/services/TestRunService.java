package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.MethodEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.mappers.TestRunMapper;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.models.response.MethodResponse;
import com.mbi.api.models.response.ProductResponse;
import com.mbi.api.models.response.TestRunResponse;
import com.mbi.api.repositories.MethodRepository;
import com.mbi.api.repositories.ProductRepository;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MethodRepository methodRepository;

    public ResponseEntity<CreatedModel> parseTestNG(TestRunModel testRunModel, String productName) throws NotFoundException {
        if (productRepository.findByName(productName).isEmpty()) {
            throw new NotFoundException(ProductEntity.class);
        }
        var productEntity = productRepository.findByName(productName).get();

        var testRunEntity = new TestRunMapper().map(testRunModel);
        testRunEntity.setProductId(productEntity.getId());
        testRunRepository.save(testRunEntity);

        var createdModel = new ModelMapper().map(testRunEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<TestRunResponse> getTestRunById(String productName, long id) throws NotFoundException {
        if (productRepository.findByName(productName).isEmpty()) {
            throw new NotFoundException(ProductEntity.class);
        }
        var productEntity = productRepository.findByName(productName).get();

        if (testRunRepository.findByIdAndProductId(id, productEntity.getId()).isEmpty()) {
            throw new NotFoundException(TestRunEntity.class);
        }
        var testRunEntity = testRunRepository.findById(id).get();

        var testRunResponse = new ModelMapper().map(testRunEntity, TestRunResponse.class);
        testRunResponse.setProductName(productEntity.getName());

        return new ResponseEntity<>(testRunResponse, HttpStatus.OK);
    }

    public ResponseEntity<List<MethodResponse>> getMethodsByStatus(String productName, long id, MethodStatus status) throws NotFoundException {
        if (productRepository.findByName(productName).isEmpty()) {
            throw new NotFoundException(ProductEntity.class);
        }
        var productEntity = productRepository.findByName(productName).get();

        if (testRunRepository.findByIdAndProductId(id, productEntity.getId()).isEmpty()) {
            throw new NotFoundException(TestRunEntity.class);
        }

        var methods = methodRepository.findAllByStatusAndTestRunId(status.name(), (int) id).get()
                .stream()
                .map(m -> new ModelMapper().map(m, MethodResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(methods, HttpStatus.OK);
    }
}
