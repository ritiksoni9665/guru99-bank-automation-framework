package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DepositPage {

    WebDriver driver;

    public DepositPage(WebDriver driver) {
        this.driver = driver;
    }

    By depositLink = By.linkText("Deposit");

    By accountNo = By.name("accountno");

    By amount = By.name("ammount");

    By description = By.name("desc");

    By submitBtn = By.name("AccSubmit");

    public void clickDeposit() {

        driver.findElement(depositLink).click();
    }

    public void depositMoney(
            String accNo,
            String amt,
            String desc) {

        driver.findElement(accountNo)
              .sendKeys(accNo);

        driver.findElement(amount)
              .sendKeys(amt);

        driver.findElement(description)
              .sendKeys(desc);

        driver.findElement(submitBtn)
              .click();
    }
}