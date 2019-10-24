package com.mbi.api.models.request.slack;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Slack request model.
 */
@ApiModel
public class SlackRequestModel {

    @NotNull
    private int testRunId;

    @ApiModelProperty(required = true, value = "test run id")
    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(int testRunId) {
        this.testRunId = testRunId;
    }
}
