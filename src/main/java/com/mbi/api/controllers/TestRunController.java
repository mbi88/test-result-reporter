package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.report.TestRunModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.models.response.TestRunDeltaResponse;
import com.mbi.api.models.response.TestRunResponse;
import com.mbi.api.services.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        return testRunService.createTestRun(testRunModel, productName);
    }

    @RequestMapping(method = GET, path = "/test-runs/{id}", produces = "application/json")
    public ResponseEntity<TestRunResponse> getTestRun(@PathVariable("id") final int id)
            throws NotFoundException {
        return testRunService.getTestRunById(id);
    }

    @RequestMapping(method = GET, path = "/test-runs", produces = "application/json")
    public ResponseEntity<List<TestRunResponse>> getAllTestRuns(
            @RequestParam(value = "productName", required = false) final String productName) throws NotFoundException {
        return testRunService.getAllTestRuns(productName);
    }

    @RequestMapping(method = GET, path = "/test-runs/{id}/delta", produces = "application/json")
    public ResponseEntity<TestRunDeltaResponse> getBuildDelta(@PathVariable("id") final int id)
            throws NotFoundException {
        return testRunService.getBuildDifference(id);
    }
}
