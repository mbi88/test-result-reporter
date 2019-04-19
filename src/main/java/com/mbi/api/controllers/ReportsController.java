package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.testng.ReportModel;
import com.mbi.api.models.response.CreatedResponse;
import com.mbi.api.services.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * A.
 */
@RestController
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @RequestMapping(method = POST, path = "/reports/testng", produces = "application/json",
            consumes = "application/xml")
    public ResponseEntity<CreatedResponse> parseTestNG(
            @RequestParam("productName") final String productName,
            @Valid @RequestBody final ReportModel reportModel) throws NotFoundException {
        return new ResponseEntity<>(reportsService.parseTestNG(reportModel, productName), HttpStatus.CREATED);
    }
}
