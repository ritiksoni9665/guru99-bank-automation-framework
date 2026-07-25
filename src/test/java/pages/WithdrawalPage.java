package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WithdrawalPage {

    WebDriver driver;

    public WithdrawalPage(WebDriver driver) {
        this.driver = driver;
    }

    By withdrawalLink = By.linkText("Withdrawal");

    By accountNo = By.name("accountno");

    By amount = By.name("ammount");

    By description = By.name("desc");

    By submitBtn = By.name("AccSubmit");

    public void clickWithdrawal() {

        driver.findElement(withdrawalLink).click();
    }

    public void withdrawMoney(
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