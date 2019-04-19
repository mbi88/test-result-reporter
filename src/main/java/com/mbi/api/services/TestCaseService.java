package com.mbi.api.services;

import com.mbi.api.entities.testrun.TestCaseEntity;
import com.mbi.api.entities.testrun.TestRunEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestCaseModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestCaseResponse;
import com.mbi.api.repositories.TestCaseRepository;
import com.mbi.api.repositories.TestRunRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

/**
 * Test case service.
 */

@Service
public class TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private TestRunRepository testRunRepository;

    public ResponseEntity<CreatedResponse> createTestCase(final int testRunId, final TestCaseModel testCaseModel)
            throws NotFoundException {
        final var testRunEntity = testRunRepository.findById(testRunId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testCaseEntity = new ModelMapper().map(testCaseModel, TestCaseEntity.class);
        testCaseEntity.setTestRunEntity(testRunEntity);
        testCaseRepository.save(testCaseEntity);

        final var createdModel = new ModelMapper().map(testCaseEntity, CreatedResponse.class);

        return new ResponseEntity<>(createdModel, HttpStatus.CREATED);
    }

    public ResponseEntity<Page<TestCaseResponse>> getMethodsByStatus(final int testRunId,
                                                                     final MethodStatus status,
                                                                     final Pageable pageable)
            throws NotFoundException {
        final var testRunEntity = testRunRepository.findById(testRunId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testCases = testCaseRepository.findAllByStatusAndTestRunEntity(status.name(), testRunEntity, pageable)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE))
                .map(m -> new ModelMapper().map(m, TestCaseResponse.class));

        return new ResponseEntity<>(testCases, HttpStatus.OK);
    }

    public ResponseEntity<Page<TestCaseResponse>> getAllTestCases(final int testRunId, final Pageable pageable)
            throws NotFoundException {
        final var testRunEntity = testRunRepository.findById(testRunId)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestRunEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testCases = testCaseRepository.findAllByTestRunEntity(testRunEntity, pageable)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(TestCaseEntity.class, NOT_FOUND_ERROR_MESSAGE))
                .map(m -> new ModelMapper().map(m, TestCaseResponse.class));

        return new ResponseEntity<>(testCases, HttpStatus.OK);
    }
}
