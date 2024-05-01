package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumDriver {
    private static SeleniumDriver seleniumDriver;

    //initWebDriver
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static ChromeOptions options;
    public final static int TIMEOUT = 30;
    public final static int PAGE_LOAD_TIMEOUT =120;

    private SeleniumDriver(){
        WebDriverManager.chromedriver().setup();
        //Disable Notifications and info bars
        options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        driver = new ChromeDriver(options);
        //Maximize Window
        driver.manage().window().maximize();
        //Define implicit and explicit default timeouts
        wait = new WebDriverWait(driver,Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
    }

    public static void openPage(String url){
        if (driver == null) {
            setUpDriver(); // Ensure driver is initialized before opening the page
        }
        driver.get(url);
    }

    public static WebDriver getDriver(){
        return driver;
    }

    public static void setUpDriver(){
        if(seleniumDriver == null){
            seleniumDriver = new SeleniumDriver();
        }
    }


    public static void tearDown(Scenario scenario){
        if(scenario.isFailed()){
            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
        if(driver != null) {
            driver.close();
            driver.quit();
        }
        seleniumDriver = null;
    }
}
