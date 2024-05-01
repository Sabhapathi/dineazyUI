package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import static steps.Login_Steps.*;

public class CreateMenu_Steps {

    @Then("user should go to add new menu")
    public void user_add_new_menu() throws InterruptedException {

        WebElement addObj = driver.findElement(By.xpath("//button[@class='me-2 btn btn-outline-darkblue']"));
//        highLighterMethod(driver,addObj);
        Thread.sleep(5000);
        addObj.click();
        WebElement createOption = driver.findElement(By.xpath("//h2[contains(text(),'Create')]"));
        wait.until(ExpectedConditions.elementToBeClickable(createOption));
//        highLighterMethod(driver,createOption);
        Assert.assertTrue(createOption.isDisplayed(),"AddNewMenu option is not displayed");
    }

    @When("user clicks {string}")
    public void user_clicks_saveorcancel(String buttonName) throws InterruptedException {
        WebElement buttonElement = driver.findElement(By.xpath("//*[contains(text(),'"+buttonName+"')]"));
        Thread.sleep(10000);
        buttonElement.click();
    }

    @Then("user should goes back to {string} page")
    public void goes_back_to_manage_menu_page(String pageHeader) {
        WebElement headerElement = driver.findElement(By.xpath("//h2[contains(text(),'"+pageHeader+"')]"));
        wait.until(ExpectedConditions.elementToBeClickable(headerElement));
        Assert.assertTrue(headerElement.isDisplayed(),"Menu option is not displayed");
    }

    @Then("user should see {string} menu item")
    public void should_see_menuitem(String menuitem) {
        WebElement menuItemElement = driver.findElement(By.xpath("//h5[contains(text(),'"+menuitem+"')]"));
        wait.until(ExpectedConditions.elementToBeClickable(menuItemElement));
        Assert.assertTrue(menuItemElement.isDisplayed(),"Menu item is not displayed");
    }

    @Then("user should see error {string}")
    public void should_see_error(String expectedErrorText) throws InterruptedException {
        WebElement errorElement = driver.findElement(By.xpath("//p[@class='text-danger']"));
//        wait.until(ExpectedConditions.visibilityOf(errorElement));
        Thread.sleep(5000);
        highLighterMethod(driver,errorElement);
        String actualErrorText = errorElement.getText();
        logger.info(actualErrorText);
        Assert.assertEquals(actualErrorText,expectedErrorText,"mismatch in error message");
    }



    @Given("user clean up {string} menu item")
    public void user_cleanup_menuitem(String menuitem) {
        WebElement menuItemElement = driver.findElement(By.xpath("//div[9]/div/div/div/div/button"));
        highLighterMethod(driver,menuItemElement);
        menuItemElement.click();
        WebElement menuItemDelete = driver.findElement(By.xpath("//div[9]/div/div/div/div/ul/li[2]/a"));
        highLighterMethod(driver,menuItemDelete);
        menuItemDelete.click();

    }


    @Given("user selects {string} as {string}")
    public void user_selects_as(String selectText, String optionSelect) {
        selectText = selectText.toLowerCase();
        if(selectText.equals("inventorytype") || selectText.equals("inventory type")){
            driver.findElement(By.xpath("//*[@id='inventoryTypeId']")).click();

        }
        else if(selectText.equals("subcategory")||selectText.equals("sub category")){
            driver.findElement(By.xpath("//*[@id='subCategoryId']")).click();

        }
        else driver.findElement(By.xpath("//*[@id='"+selectText+"Id']")).click();
        driver.findElement(By.xpath("//*[contains(text(),'"+optionSelect+"')]")).click();
    }

    public static void jsClickMethod(WebElement element){

        JavascriptExecutor js =(JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()",element);
    }

    @And("user enters {string} as {string}")
    public void userEntersAs(String selectText, String texttoEnter) {
        selectText = selectText.toLowerCase();
        driver.findElement(By.xpath("//*[@id='"+selectText+"']")).sendKeys(texttoEnter);
    }
}
