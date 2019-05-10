package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestRunModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;
import com.mbi.api.services.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Test run controller.
 */
@RestController
public class TestRunController {

    @Autowired
    private TestRunService testRunService;

    @RequestMapping(method = POST, path = "/test-runs", produces = "application/json", consumes = "application/json")
    public ResponseEntity<CreatedResponse> create(
            @RequestParam("productName") final String productName,
            @Valid @RequestBody final TestRunModel testRunModel) throws NotFoundException {
        return new ResponseEntity<>(testRunService.createTestRun(testRunModel, productName), HttpStatus.CREATED);
    }

    @RequestMapping(method = GET, path = "/test-runs/{id}", produces = "application/json")
    public ResponseEntity<TestRunResponse> getTestRun(@PathVariable("id") final int id) throws NotFoundException {
        return new ResponseEntity<>(testRunService.getTestRunById(id), HttpStatus.OK);
    }

    @RequestMapping(method = GET, path = "/test-runs", produces = "application/json")
    public ResponseEntity<Page<TestRunResponse>> getAllTestRuns(
            @RequestParam(value = "productName", required = false) final String productName,
            @RequestParam(value = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") final Integer size)
            throws NotFoundException {
        return new ResponseEntity<>(testRunService.getAllTestRuns(productName, PageRequest.of(page, size)),
                HttpStatus.OK);
    }

    @RequestMapping(method = GET, path = "/test-runs/{id}/delta", produces = "application/json")
    public ResponseEntity<TestRunDeltaResponse> getBuildDelta(@PathVariable("id") final int id)
            throws NotFoundException {
        return new ResponseEntity<>(testRunService.getBuildDifference(id), HttpStatus.OK);
    }
}
