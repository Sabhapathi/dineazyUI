package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class Login_Steps {

    public static final Logger logger = (Logger) LogManager.getLogger();

//    public static WebDriver driver;
    public static WebDriverWait wait;
    public String username = "sabhapathi.a";
    public String accesskey = "dzeZW9CufVlyR5BTHLOPvCBIyZ8f0anedLETjes2fFUEyrL279";
    public static RemoteWebDriver driver = null;

    public String gridURL = "http://localhost:4444/wd/hub";
//    public String gridURL = "@hub.lambdatest.com/wd/hub";
    public final static int TIMEOUT = 60;
    public final static int PAGE_LOAD_TIMEOUT =120;

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();

        //Default Browser local
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
//        options.addArguments("headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        wait = new WebDriverWait(driver, Duration.ofSeconds(120));

        //Lambda Test Browser Options

//        ChromeOptions browserOptions = new ChromeOptions();
//        browserOptions.setPlatformName("Windows 10");
//        browserOptions.addArguments("--disable-notifications");
//        browserOptions.setBrowserVersion("dev");
//        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
//        ltOptions.put("username", "sabhapathi.a");
//        ltOptions.put("accessKey", "dzeZW9CufVlyR5BTHLOPvCBIyZ8f0anedLETjes2fFUEyrL279");
//        ltOptions.put("project", "Dineazy UI Tests");
////        ltOptions.put("selenium_version", "4.0.0");
//        ltOptions.put("w3c", true);
//        browserOptions.setCapability("LT:Options", ltOptions);
//        try {
//            driver = new RemoteWebDriver(new URL("https://"+username + ":" + accesskey + gridURL), browserOptions);
//        } catch (MalformedURLException e) {
//            System.out.println("Invalid grid URL");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        //Local Browser installed on BitBucket Server

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-notifications");
//        options.addArguments("headless");
//
//        try {
//            driver = new RemoteWebDriver(new URL(gridURL), options);
//            driver.manage().window().maximize();
//        } catch (MalformedURLException e) {
//            System.out.println("Invalid grid URL");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver,Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(PAGE_LOAD_TIMEOUT));
    }

    @After
    public void tearDown(Scenario scenario){

        if(scenario.isFailed()){
            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Screenshot");
        }
        if(driver != null) {
            driver.close();
            driver.quit();
        }
    }

    @Given("user is on Dineazy website")
    public void user_is_on_dineazy_website() {
        driver.get("https://dineazy-dev.elaachi.com/");
    }


    @When("user enters {string} and {string}")
    public void user_enters_and_password(String username, String pwd) {
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(username);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(pwd);


    }

    @And("user login")
    public void user_login() {
        WebElement signin = driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/ui-button/button"));
        if(signin.isDisplayed())
        {
            signin.click();
        }
        else System.out.println("Sign-in button is disabled");
    }

    @Given("user login to Dineazy")
    public void user_login_to_dineazy() throws InterruptedException {
        driver.get("https://dineazy-dev.elaachi.com/");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("girish.pr@itprofound.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("Test@1234");
        WebElement signin = driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/ui-button/button"));
        signin.click();
        Thread.sleep(30000);
        WebElement companyLogo = driver.findElement(By.xpath("/html/body/app-root/app-shell/app-topbar/header/div/span/img"));
        wait.until(ExpectedConditions.elementToBeClickable(companyLogo));
        Assert.assertTrue(companyLogo.isDisplayed());
        highLighterMethod(driver,companyLogo);


    }

    @Then("user should see Home page")
    public void user_should_see_home_page() throws InterruptedException {
        WebElement companyLogo = driver.findElement(By.xpath("/html/body/app-root/app-shell/app-topbar/header/div/span/img"));
        wait.until(ExpectedConditions.elementToBeClickable(companyLogo));
        Assert.assertTrue(companyLogo.isDisplayed());
    }

    @Then("user should see {string}")
    public void user_should_see_error(String expectedErrorMsg) throws InterruptedException {

        WebElement actualErrorObject = driver.findElement(By.xpath("/html/body/app-root/app-login/div/div/form/p\n"));
        Thread.sleep(5000);
        Assert.assertEquals(actualErrorObject.getText(),expectedErrorMsg,"Match not found");
    }

    @When("user hides side menu")
    public void user_hides_menu() throws InterruptedException {
        WebElement sidebar = driver.findElement(By.xpath("//app-topbar/header/div/a/i"));
        sidebar.click();

    }




//HIGHLIGHTER CODE
    public static void highLighterMethod(WebDriver driver, WebElement element){
        JavascriptExecutor js =(JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'border:2px solid red; background:yellow')",element);
    }


}
