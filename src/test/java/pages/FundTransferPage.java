package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FundTransferPage {

    WebDriver driver;

    public FundTransferPage(WebDriver driver) {
        this.driver = driver;
    }

    By fundTransferLink = By.linkText("Fund Transfer");

    By payerAccount = By.name("payersaccount");

    By payeeAccount = By.name("payeeaccount");

    By amount = By.name("ammount");

    By description = By.name("desc");

    By submitBtn = By.name("AccSubmit");

    public void clickFundTransfer() {

        driver.findElement(fundTransferLink).click();
    }

    public void transferFunds(
            String payerAcc,
            String payeeAcc,
            String transferAmount,
            String desc) {

        driver.findElement(payerAccount)
              .sendKeys(payerAcc);

        driver.findElement(payeeAccount)
              .sendKeys(payeeAcc);

        driver.findElement(amount)
              .sendKeys(transferAmount);

        driver.findElement(description)
              .sendKeys(desc);

        driver.findElement(submitBtn)
              .click();
    }
}