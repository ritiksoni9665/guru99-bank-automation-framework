package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BalanceEnquiryPage {

    WebDriver driver;

    public BalanceEnquiryPage(WebDriver driver) {

        this.driver = driver;
    }

    By balanceEnquiryLink =
            By.linkText("Balance Enquiry");

    By accountNo =
            By.name("accountno");

    By submitBtn =
            By.name("AccSubmit");

    public void clickBalanceEnquiry() {

        driver.findElement(
                balanceEnquiryLink)
                .click();
    }

    public void checkBalance(
            String accountId) {

        driver.findElement(
                accountNo)
                .sendKeys(accountId);

        driver.findElement(
                submitBtn)
                .click();
    }

    public String getBalance() {

        return driver.findElement(
                By.xpath(
                "//td[text()='Balance']/following-sibling::td"))
                .getText();
    }
}