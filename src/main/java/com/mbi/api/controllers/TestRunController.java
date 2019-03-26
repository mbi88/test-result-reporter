package com.mbi.api.controllers;

import com.mbi.api.enums.MethodStatus;
import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.models.response.MethodResponse;
import com.mbi.api.models.response.TestRunResponse;
import com.mbi.api.services.TestRunService;
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

@RestController
public class TestRunController {

    @Autowired
    private TestRunService testRunService;

    @RequestMapping(method = POST,
            path = "/products/{productName}/reporters/testng/test-runs",
            produces = "application/json",
            consumes = "application/xml")
    public ResponseEntity<CreatedModel> parseTestNG(
            @PathVariable(value = "productName") String productName,
            @Valid @RequestBody TestRunModel testRunModel) throws NotFoundException {
        return testRunService.parseTestNG(testRunModel, productName);
    }

    @RequestMapping(method = GET,
            path = "/products/{productName}/reporters/testng/test-runs/{id}",
            produces = "application/json")
    public ResponseEntity<TestRunResponse> getTestRun(
            @PathVariable(value = "productName") String productName,
            @PathVariable(value = "id") long id) throws NotFoundException {
        return testRunService.getTestRunById(productName, id);
    }

    @RequestMapping(method = GET,
            path = "/products/{productName}/reporters/testng/test-runs/{id}/failed",
            produces = "application/json")
    public ResponseEntity<List<MethodResponse>> getFailedTestCases(
            @PathVariable(value = "productName") String productName,
            @PathVariable(value = "id") long id) throws NotFoundException {
        return testRunService.getMethodsByStatus(productName, id, MethodStatus.FAIL);
    }

    @RequestMapping(method = GET,
            path = "/products/{productName}/reporters/testng/test-runs/{id}/passed",
            produces = "application/json")
    public ResponseEntity<List<MethodResponse>> getPassedTestCases(
            @PathVariable(value = "productName") String productName,
            @PathVariable(value = "id") long id) throws NotFoundException {
        return testRunService.getMethodsByStatus(productName, id, MethodStatus.PASS);
    }
}
