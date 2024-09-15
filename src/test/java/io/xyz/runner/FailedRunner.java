package io.xyz.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/html-reports/failed-report.html",      // HTML report for failed tests
                "json:target/json-reports/failed-report.json"       // JSON report for failed tests
        },
        features = "@target/rerun.txt",   // Rerun the failed scenarios from the rerun.txt file
        glue = "io/xyz/layers/common/steps_defs",   // Same step definitions as CukesRunner
        monochrome = true,              // Better readability
        publish = false                 // Don't publish report online
)
public class FailedRunner {
}