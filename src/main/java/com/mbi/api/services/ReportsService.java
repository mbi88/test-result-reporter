package com.mbi.api.services;

import com.mbi.api.entities.product.ProductEntity;
import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestCaseModel;
import com.mbi.api.models.request.report.TestRunModel;
import com.mbi.api.models.request.testng.ClassModel;
import com.mbi.api.models.request.testng.MethodModel;
import com.mbi.api.models.request.testng.ReportModel;
import com.mbi.api.models.request.testng.SuiteModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_ERROR_MESSAGE;
import static com.mbi.api.exceptions.ExceptionSupplier.NOT_FOUND_SUPPLIER;

/**
 * Test run service.
 */
@Service
public class ReportsService extends BaseService {

    @Autowired
    private TestRunService testRunService;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public CreatedResponse parseTestNG(final ReportModel reportModel, final String productName)
            throws NotFoundException {
        productRepository.findByName(productName)
                .orElseThrow(NOT_FOUND_SUPPLIER.apply(ProductEntity.class, NOT_FOUND_ERROR_MESSAGE));

        final var testRunModel = mapper.map(reportModel, TestRunModel.class);

        // Set duration
        int testsDuration = 0;
        for (var suite : reportModel.getSuites()) {
            testsDuration += Integer.parseInt(suite.getDuration());
        }
        testRunModel.setDuration(testsDuration);

        final var response = testRunService.createTestRun(testRunModel, productName);

        // Create test cases
        for (var testClass : getClasses(reportModel.getSuites())) {
            for (var method : testClass.getMethods()) {
                createTestCase(method, testClass.getName(), response.getId());
            }
        }

        return response;
    }

    private Set<ClassModel> getClasses(final Set<SuiteModel> suiteModels) {
        final Set<ClassModel> classes = new HashSet<>();
        for (var suite : suiteModels) {
            if (Objects.isNull(suite.getTests())) {
                continue;
            }
            for (var test : suite.getTests()) {
                classes.addAll(test.getClasses());
            }
        }

        return classes;
    }

    private void createTestCase(final MethodModel method, final String className, final int testRunId)
            throws NotFoundException {
        final var testCaseModel = new TestCaseModel();
        testCaseModel.setName(method.getName());
        testCaseModel.setClassName(className);
        testCaseModel.setDuration(Integer.parseInt(method.getDuration()));
        // Set status
        final var status = Stream.of(MethodStatus.values())
                .filter(s -> s.name().toLowerCase().startsWith(method.getStatus().toLowerCase()))
                .map(Enum::name)
                .collect(Collectors.joining());
        testCaseModel.setStatus(MethodStatus.valueOf(status));
        // Set exception
        if (Objects.nonNull(method.getException())) {
            testCaseModel.setException(method.getException().getException().trim());
        }

        testCaseService.createTestCase(testRunId, testCaseModel);
    }
}
