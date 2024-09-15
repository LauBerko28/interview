package io.xyz.runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/html-reports/cucumber-report.html",     // HTML report
                "json:target/json-reports/json-report.json",         // JSON report
                "rerun:target/rerun.txt",                            // Rerun file
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"  // Extent reports
        },
        features = "src/test/resources/features",   // Path to feature files
        glue = "io/xyz/layers/common/steps_defs",   // Path to step definitions
        tags = "@wiki",                             // Run scenarios tagged with @wiki
        dryRun = false,                             // Run actual tests, not just validation
        monochrome = true,                          // Better console output readability
        publish = false                             // Don't publish Cucumber report online
)
public class CukesRunner {
}