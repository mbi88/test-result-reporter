package com.mbi.api.controllers;

import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestCaseModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestCaseResponse;
import com.mbi.api.services.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Page<TestCaseResponse>> getAll(
            @PathVariable("testRunId") final int testRunId,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size)
            throws NotFoundException {
        return testCaseService.getAllTestCases(testRunId, PageRequest.of(page, size));
    }

    @RequestMapping(method = GET, path = "/test-runs/{testRunId}/test-cases/failed", produces = "application/json")
    public ResponseEntity<Page<TestCaseResponse>> getFailedTestCases(
            @PathVariable("testRunId") final int testRunId,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size)
            throws NotFoundException {
        return testCaseService.getMethodsByStatus(testRunId, MethodStatus.FAILED, PageRequest.of(page, size));
    }

    @RequestMapping(method = GET, path = "/test-runs/{testRunId}/test-cases/passed", produces = "application/json")
    public ResponseEntity<Page<TestCaseResponse>> getPassedTestCases(
            @PathVariable("testRunId") final int testRunId,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size)
            throws NotFoundException {
        return testCaseService.getMethodsByStatus(testRunId, MethodStatus.PASSED, PageRequest.of(page, size));
    }
}
