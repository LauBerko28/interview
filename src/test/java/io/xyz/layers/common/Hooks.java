package io.xyz.layers.common;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.xyz.utils.browser_utils.BrowserUtils;
import io.xyz.utils.common_utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private static final Logger LOG = LogManager.getRootLogger();

    @Before
    public void setUp(Scenario scenario){
        Driver.getDriver();
        BrowserUtils.myScenario = scenario;
        LOG.info("............ START AUTOMATION ................ FOR INTERVIEW");
    }

    @Before(value = "@smoke")
    public void setUpd(Scenario scenario){
        Driver.getDriver();
        BrowserUtils.myScenario = scenario; // you can have as many as needed but is not really needed
    }

    @After
    public void tearDown(Scenario scenario){
        // only takes a screenshot when scenario is failed
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
         Driver.closeDriver();
        LOG.info("............ END AUTOMATION ................ FOR INTERVIEW");
    }

    @AfterStep
    public void screenShot(Scenario scenario){
        final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
    }
}

