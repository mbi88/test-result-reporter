package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;
import com.mbi.api.repositories.ProductRepository;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

/**
 * Test run service.
 */
@Service
public class TestRunService {

    @Autowired
    private TestRunRepository testRunRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<CreatedResponse> createTestRun(final TestRunModel testRunModel,
                                                         final String productName) throws NotFoundException {
        final var productEntity = productRepository.findByName(productName)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testRunEntity = new ModelMapper().map(testRunModel, TestRunEntity.class);
        // Set test run product
        testRunEntity.setProduct(productEntity);
        // Set test run status
        final Predicate<Integer> zero = i -> i == 0;
        final boolean successful = zero.test(testRunModel.getFailed()) && zero.test(testRunModel.getSkipped());
        testRunEntity.setSuccessful(successful);
        // Save
        testRunRepository.save(testRunEntity);

        final var createdModel = new ModelMapper().map(testRunEntity, CreatedResponse.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<TestRunResponse> getTestRunById(final int id) throws NotFoundException {
        final var testRunEntity = testRunRepository.findById(id)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testRunResponse = new ModelMapper().map(testRunEntity, TestRunResponse.class);

        return new ResponseEntity<>(testRunResponse, HttpStatus.OK);
    }

    public ResponseEntity<List<TestRunResponse>> getAllTestRuns(final String productName) throws NotFoundException {
        final List<TestRunEntity> testRunEntities;

        if (Objects.isNull(productName) || productName.isBlank()) {
            testRunEntities = (List<TestRunEntity>) testRunRepository.findAll();
        } else {
            final var product = productRepository.findByName(productName)
                    .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));
            testRunEntities = testRunRepository.findAllByProduct(product)
                    .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, "No test runs in this product"));
        }

        final var testRuns = testRunEntities
                .stream()
                .map(testRun -> new ModelMapper().map(testRun, TestRunResponse.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(testRuns, HttpStatus.OK);
    }

    public ResponseEntity<TestRunDeltaResponse> getBuildDifference(final int id) throws NotFoundException {
        final var currentTestRun = testRunRepository
                .findById(id)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final int prevTestRinId = testRunRepository
                .findPreviousById(currentTestRun.getId(), currentTestRun.getProduct().getId())
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));
        final var prevTestRun = testRunRepository
                .findById(prevTestRinId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var deltaResponse = new TestRunDeltaResponse();
        deltaResponse.setDurationDiff(currentTestRun.getDuration() - prevTestRun.getDuration());
        deltaResponse.setTotalDiff(currentTestRun.getTotal() - prevTestRun.getTotal());
        deltaResponse.setPassedDiff(currentTestRun.getPassed() - prevTestRun.getPassed());
        deltaResponse.setFailedDiff(currentTestRun.getFailed() - prevTestRun.getFailed());
        deltaResponse.setSkippedDiff(currentTestRun.getSkipped() - prevTestRun.getSkipped());

        return new ResponseEntity<>(deltaResponse, HttpStatus.OK);
    }
}
