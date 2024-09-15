package io.xyz.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions (
        plugin = {"html:target/html-reports/cucumber-report.html",
                "json:target/json-reports/json-report.json"},
        features = "@target/rerun.txt",
        glue = "io/xyz/layers/common/steps_defs",
        monochrome = true,
        publish = false
)

public class FailedRunner {

}
