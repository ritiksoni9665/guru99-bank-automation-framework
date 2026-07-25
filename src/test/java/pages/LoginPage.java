package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    public LoginPage(WebDriver driver) {

        this.driver = driver;
    }

    By userId = By.name("uid");

    By password = By.name("password");

    By loginBtn = By.name("btnLogin");

    public void enterUserId(String uid) {

        driver.findElement(userId).sendKeys(uid);
    }

    public void enterPassword(String pass) {

        driver.findElement(password).sendKeys(pass);
    }

    public void clickLogin() {

        driver.findElement(loginBtn).click();
    }
    public void login(String user, String pass) {

        driver.findElement(userId).sendKeys(user);

        driver.findElement(password).sendKeys(pass);

        driver.findElement(loginBtn).click();
    }
}