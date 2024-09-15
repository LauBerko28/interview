package io.xyz.utils.common_utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {
     /*
    Creating the private constructor so this class's object is not reachable from outside
     */

    private Driver(){}

    /*
    making driver instance private
    static - run before everything else and use ins static method
     */


    // private static WebDriver driver;

    //implement threadLocal to achieve multi thread locally
    private static InheritableThreadLocal<WebDriver> driverpool= new InheritableThreadLocal<>();

    /*
    reusable method that will return the same driver instance everytime called
     */

    /**
     * singleton patter
     * @return
     */
    public static WebDriver getDriver(){
        if(driverpool.get()==null){
            String browserType = ConfigurationReader.getProperties("browser");
            switch (browserType.toLowerCase()){
                case "chrome":
                    driverpool.set(new ChromeDriver());
                    driverpool.get().manage().window().maximize();
                    driverpool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
                    break;
                case "firefox":
                    driverpool.set(new FirefoxDriver());
                    driverpool.get().manage().window().maximize();
                    driverpool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
                    break;
                case "safari":
                    driverpool.set(new SafariDriver());
                    driverpool.get().manage().window().maximize();
                    driverpool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
                    break;
                case "headless":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("headless");
                    driverpool.set(new ChromeDriver(options));
                    driverpool.get().manage().window().maximize();
                    driverpool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(ConfigurationReader.getProperties("timeouts"))));
            }

        }
        return driverpool.get();
    }

    /**
     * closing driver
     * @author Me
     */
    public static void closeDriver(){
        if(driverpool.get() !=null){
            driverpool.get().quit();
            //  driver = null;
            driverpool.remove();
        }
    }
}
