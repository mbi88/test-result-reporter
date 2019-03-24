package parser;

import java.util.List;

public interface Parser {

    TestResult getTestResult();

    List<TestCase> getAllTestCases();

    List<TestCase> getFailedTestCases();

    List<TestCase> getSuccessTestCases();

    List<TestCase> getSkippedTestCases();

    List<TestCase> getIgnoredTestCases();
}
