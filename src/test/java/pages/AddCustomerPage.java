package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utilities.WaitUtils;

public class AddCustomerPage {

    WebDriver driver;

    public AddCustomerPage(WebDriver driver) {
        this.driver = driver;
    }

    By newCustomerLink = By.linkText("New Customer");

    By customerName = By.name("name");
    By dob = By.name("dob");
    By maleGender = By.xpath("//input[@value='m']");
    By address = By.name("addr");
    By city = By.name("city");
    By state = By.name("state");
    By pin = By.name("pinno");
    By mobile = By.name("telephoneno");
    By email = By.name("emailid");
    By password = By.name("password");
    By submitBtn = By.name("sub");

    // Validation Messages
    By nameError = By.id("message");
    By pinError = By.id("message6");

    public void clickNewCustomer() {
        driver.findElement(newCustomerLink).click();
    }

    public void enterCustomerName(String name) {
        driver.findElement(customerName).clear();
        driver.findElement(customerName).sendKeys(name);
    }

    public void enterPin(String pinValue) {
        driver.findElement(pin).clear();
        driver.findElement(pin).sendKeys(pinValue);
    }

    public void clickSubmit() {
        driver.findElement(submitBtn).click();
    }

    public String getNameValidationMessage() {
        return driver.findElement(nameError).getText();
    }

    public String getPinValidationMessage() {
        return driver.findElement(pinError).getText();
    }

    public void addCustomer(
            String name,
            String birthDate,
            String addr,
            String cityName,
            String stateName,
            String pinNo,
            String mobileNo,
            String emailId,
            String pwd) {

        driver.findElement(customerName).clear();
        driver.findElement(customerName).sendKeys(name);

        driver.findElement(maleGender).click();

        WebElement dobField = driver.findElement(dob);
        dobField.clear();
        dobField.sendKeys(birthDate);
        dobField.sendKeys(Keys.TAB);

        driver.findElement(address).clear();
        driver.findElement(address).sendKeys(addr);

        driver.findElement(city).clear();
        driver.findElement(city).sendKeys(cityName);

        driver.findElement(state).clear();
        driver.findElement(state).sendKeys(stateName);

        driver.findElement(pin).clear();
        driver.findElement(pin).sendKeys(pinNo);

        driver.findElement(mobile).clear();
        driver.findElement(mobile).sendKeys(mobileNo);

        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(emailId);

        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pwd);

        driver.findElement(submitBtn).click();
    }

    public String getCustomerId() {

        By successMessage =
                By.xpath("//p[contains(text(),'Customer Registered Successfully')]");

        By customerIdLocator =
                By.xpath("//td[text()='Customer ID']/following-sibling::td");

        WaitUtils.waitForVisibility(driver, successMessage);

        return WaitUtils.waitForVisibility(
                driver,
                customerIdLocator)
                .getText()
                .trim();
    }
}