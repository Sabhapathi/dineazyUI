package steps;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static steps.Login_Steps.driver;
import static steps.Login_Steps.highLighterMethod;

public class Home_Steps {

    @When("user navigates to {string} application menu")
    @When("user navigates to {string} option")
    public void user_navigates_to_menu(String Menu) throws InterruptedException {
        WebElement menuElement = driver.findElement(By.xpath("//*[contains(text(),'"+Menu+"')]"));
        menuElement.click();
        highLighterMethod(driver,menuElement);
    }




}
