package com.mbi.api.controllers;

import com.mbi.api.exceptions.NotFoundException;
import com.mbi.api.models.request.TestRunModel;
import com.mbi.api.models.response.CreatedModel;
import com.mbi.api.services.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TestRunController {

    @Autowired
    private TestRunService testRunService;

    @RequestMapping(method = POST, path = "/parse/testng", produces = "application/json", consumes = "application/xml")
    public ResponseEntity<CreatedModel> parseTestNG(@Valid @RequestBody TestRunModel testRunModel,
                                                    @RequestParam(value = "productName") String productName) throws NotFoundException {
        return testRunService.parseTestNG(testRunModel, productName);
    }
}
