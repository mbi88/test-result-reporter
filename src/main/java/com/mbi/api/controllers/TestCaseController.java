package com.mbi.api.controllers;

import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestCaseModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestCaseResponse;
import com.mbi.api.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Test case controller.
 */
@RestController
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @RequestMapping(method = POST, path = "/test-runs/{testRunId}/test-cases", produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<CreatedResponse> create(@PathVariable("testRunId") final int testRunId,
                                                  @Valid @RequestBody final TestCaseModel testCaseModel)
            throws NotFoundException {
        return testCaseService.createTestCase(testRunId, testCaseModel);
    }

    @RequestMapping(method = GET, path = "/test-runs/{testRunId}/test-cases", produces = "application/json")
    public ResponseEntity<List<TestCaseResponse>> getAll(@PathVariable("testRunId") final int testRunId)
            throws NotFoundException {
        return testCaseService.getAllTestCases(testRunId);
    }

    @RequestMapping(method = GET, path = "/test-runs/{testRunId}/test-cases/failed", produces = "application/json")
    public ResponseEntity<List<TestCaseResponse>> getFailedTestCases(@PathVariable("testRunId") final int testRunId)
            throws NotFoundException {
        return testCaseService.getMethodsByStatus(testRunId, MethodStatus.FAILED);
    }

    @RequestMapping(method = GET, path = "/test-runs/{testRunId}/test-cases/passed", produces = "application/json")
    public ResponseEntity<List<TestCaseResponse>> getPassedTestCases(@PathVariable("testRunId") final int testRunId)
            throws NotFoundException {
        return testCaseService.getMethodsByStatus(testRunId, MethodStatus.PASSED);
    }
}
