package io.xyz.utils.browser_utils;

import io.cucumber.java.Scenario;
import io.xyz.utils.common_utils.ConfigurationReader;
import io.xyz.utils.common_utils.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class BrowserUtils {
    public static Scenario myScenario;
    //private static Logger LOG = LogManager.getLogger();
    public  static Logger LOG = LogManager.getLogger();

    public static void takeScreenshot(){
        try {
            myScenario.log("Current url is: " + Driver.getDriver().getCurrentUrl());
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            myScenario.attach(screenshot, "image/png", myScenario.getName());
        }catch (WebDriverException wbd){
            wbd.getMessage();
        } catch (ClassCastException cce){
            cce.getMessage();
        }
    }


    /**
     * performs a pause
     * @param seconds
     * @author Me
     */
    public static void justWait (int seconds){
        try{
            Thread.sleep(seconds);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * validate if driver switched to expected url and title
     *
     * @param driver
     * @param expectedUrl
     * @param expectedTitle
     * @author Me
     * implements assertion
     */
    public static void switchWindowAndValidate(WebDriver driver, String expectedUrl, String expectedTitle) {
        // to lowercase the parameters in order to avoid typos
        expectedTitle = expectedTitle.toLowerCase();
        expectedUrl = expectedUrl.toLowerCase();

        // get all window handles, switch one by one and each time check if the url matches expected url to stop
        Set<String> windowHandles = driver.getWindowHandles();
        for (String each : windowHandles) {
            driver.switchTo().window(each);
            if (driver.getCurrentUrl().toLowerCase().contains(expectedUrl)) {
                break;
            }
        }

        // after stopping an expected url, validate the title
        assertTrue(driver.getTitle().toLowerCase().contains(expectedTitle));
    }

    /**
     * @param driver
     * @param targetTitle
     * @author Me
     */
    public static void switchToWindow(WebDriver driver, String targetTitle) {
        String origin = driver.getWindowHandle();
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
            if (driver.getTitle().contains(targetTitle)) {
                return;
            }
        }
        driver.switchTo().window(origin);
    }

    /**
     * @param driver
     * @param expectedTitle returns void, assertion is implemented
     * @author Me
     */
    public static void validateTitle(WebDriver driver, String expectedTitle) {
        assertTrue(driver.getTitle().contains(expectedTitle));
    }


    /**
     * Moves the mouse to given element
     * @param element on which to hover
     * @author Me
     */
    public static void hover(WebElement element){
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * Scrolls down to an element using JavaScript
     * @param element
     * @author Me
     */
    public static void scrollToElement(WebElement element){
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Clicks on an element using JavaScript
     * @param element
     * @author Me
     */
    public static void clickWithJS(WebElement element){
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }

    /**
     * Performs double click action on an element
     * @param element
     * @author Me
     */
    public static void doubleClick(WebElement element){
        new Actions(Driver.getDriver()).doubleClick(element).perform();
    }

    /**
     * Waits for the provided element to be visible on the page
     * @param element
     * @param timeToWaitInSec
     * @return
     * @author Me
     */
    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for provided element to be clickable
     * @param element
     * @param timeout
     * @return
     * @author Me
     */
    public static WebElement waitForClickable (WebElement element, int timeout){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for the provided element to be invisible on the page
     * @param element
     * @param timeToWaitInSec
     * @author Me
     */
    public static void waitForInVisibility(WebElement element, int timeToWaitInSec){
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeToWaitInSec));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }


    public static List<String> getElementsText(List<WebElement> elements){
        List <String> elementsText = new ArrayList<>();
        for (WebElement element : elements){
            elementsText.add(element.getText());
        }
        return elementsText;
    }

    public static List<String> getElementsTextWithStream (List<WebElement> elements){
        return elements.stream()
                .map(x->x.getText())
                .collect(Collectors.toList());
    }

    public static List<String> getElementsTextWithStream2 (List<WebElement> elements){
        return elements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            LOG.info("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeOutInSeconds));
            wait.until(expectation);
        } catch (Throwable error) {
            LOG.info(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            try {
                element.isDisplayed();
                break;
            } catch (StaleElementReferenceException st) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (WebDriverException we) {
                y++;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void waitUntilPageLoad() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeout"))));
        wait.until((d) -> {
            Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) Driver.getDriver())
                    .executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded)
                LOG.info("Document is loading");
            return isPageLoaded;
        });
    }

    public static void createFileWithContent(String filePath, String content) {
        File file = new File(filePath);

        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            try {
                fw.write(content);
            } catch (Exception e) {
                LOG.debug("Error during FileWriter append. " + e.getMessage(), e.getCause());
            } finally {
                try {
                    fw.close();
                } catch (Exception e) {
                    LOG.debug("Error during FileWriter close. " + e.getMessage(), e.getCause());
                }
            }

        } catch (IOException e) {
            LOG.debug(e.getMessage(), e.getCause());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        }
    }
}
