package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.mappers.TestRunMapper;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.models.response.MethodResponse;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MethodRepository methodRepository;

    public ResponseEntity<CreatedModel> parseTestNG(final TestRunModel testRunModel, final String productName) throws NotFoundException {
        var productEntity = productRepository.findByName(productName)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        var testRunEntity = new TestRunMapper().map(testRunModel);
        testRunEntity.setProduct(productEntity);
        testRunRepository.save(testRunEntity);

        var createdModel = new ModelMapper().map(testRunEntity, CreatedModel.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<TestRunResponse> getTestRunById(final long id) throws NotFoundException {
        var testRunEntity = testRunRepository.findById(id)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        var testRunResponse = new ModelMapper().map(testRunEntity, TestRunResponse.class);

        return new ResponseEntity<>(testRunResponse, HttpStatus.OK);
    }

    public ResponseEntity<List<TestRunResponse>> getAllTestRuns(final String productName) throws NotFoundException {
        List<TestRunEntity> testRunEntities;

        if (Objects.isNull(productName) || productName.isBlank()) {
            testRunEntities = (List<TestRunEntity>) testRunRepository.findAll();
        } else {
            var product = productRepository.findByName(productName)
                    .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));
            testRunEntities = testRunRepository.findAllByProduct(product)
                    .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, "No test runs in this product"));
        }

        var testRuns = testRunEntities
                .stream()
                .map(testRun -> new ModelMapper().map(testRun, TestRunResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(testRuns, HttpStatus.OK);
    }

    public ResponseEntity<List<MethodResponse>> getMethodsByStatus(final long id, final MethodStatus status) throws NotFoundException {
        testRunRepository.findById(id)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        var methods = methodRepository.findAllByStatusAndTestRunId(status.name(), (int) id).get()
                .stream()
                .map(m -> new ModelMapper().map(m, MethodResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(methods, HttpStatus.OK);
    }
}
