package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddCustomerPage;
import pages.BalanceEnquiryPage;
import pages.DepositPage;
import pages.LoginPage;
import pages.NewAccountPage;
import pages.WithdrawalPage;
import utilities.ConfigReader;
import utilities.TestLogger;

public class TransactionTest extends BaseTest {

    private String customerId;
    private String accountId;

    @BeforeClass
    public void startModule() {

        TestLogger.print(
                "\n==================================================\n" +
                "MODULE 4 : TRANSACTIONS\n" +
                "==================================================\n");
    }

    private boolean isHttp500Present(WebDriver driver) {

        String page = driver.getPageSource().toLowerCase();

        return page.contains("http error 500")
                || page.contains("http status 500")
                || page.contains("internal server error");
    }

    private void relogin(WebDriver driver) {

        try {

            driver.get(new ConfigReader().getUrl());

            LoginPage login = new LoginPage(driver);

            login.login(
                    new ConfigReader().getUsername(),
                    new ConfigReader().getPassword());

            TestLogger.print(
                    "Re-login successful\n");

        } catch (Exception e) {

            TestLogger.print(
                    "Re-login failed : "
                            + e.getMessage()
                            + "\n");
        }
    }

    /*
     * TC_TRANS_001 : DEPOSIT
     */
    @Test(priority = 1,description = "TC_TRANS_001 : Deposit")
    public void depositTest() {

        WebDriver driver = BaseTest.getDriver();

        driver.get(new ConfigReader().getUrl());

        LoginPage login = new LoginPage(driver);

        login.login(
                new ConfigReader().getUsername(),
                new ConfigReader().getPassword());

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        String email =
                "txn"
                        + System.currentTimeMillis()
                        + "@gmail.com";

        customer.addCustomer(
                "Ritik",
                "01-01-1999",
                "Delhi",
                "Delhi",
                "Delhi",
                "123456",
                "9876543210",
                email,
                "pass");

        if (isHttp500Present(driver)) {

            TestLogger.blocked(
                    "TC_TRANS_001 Deposit",
                    "Guru99 returned HTTP 500");

            relogin(driver);
            return;
        }

        customerId =
                customer.getCustomerId();

        NewAccountPage account =
                new NewAccountPage(driver);

        account.clickNewAccount();

        account.createAccount(
                customerId,
                "Savings",
                "10000");

        if (isHttp500Present(driver)) {

            TestLogger.blocked(
                    "TC_TRANS_001 Deposit",
                    "Guru99 returned HTTP 500");

            relogin(driver);
            return;
        }

       
        accountId = account.getAccountId();

        System.out.println("Customer ID = " + customerId);
        System.out.println("Account ID = " + accountId);
        DepositPage deposit =
                new DepositPage(driver);

        deposit.clickDeposit();

        deposit.depositMoney(
                accountId,
                "2000",
                "Deposit");

        if (isHttp500Present(driver)) {

            TestLogger.fail(
                    "TC_TRANS_001 Deposit",
                    "Guru99 returned HTTP 500");

            relogin(driver);

            throw new AssertionError(
                    "Guru99 returned HTTP 500");
        }

        TestLogger.pass(
                "TC_TRANS_001 Deposit");
    }

    /*
     * TC_TRANS_002 : WITHDRAWAL
     */
    @Test(priority = 2,description = "TC_TRANS_002 : Withdrawal")
    public void withdrawalTest() {

        WebDriver driver =
                BaseTest.getDriver();

        try {

            WithdrawalPage w =
                    new WithdrawalPage(driver);

            w.clickWithdrawal();

            w.withdrawMoney(
                    accountId,
                    "1000",
                    "Withdraw");

            if (isHttp500Present(driver)) {

            	TestLogger.fail(
            	        "TC_TRANS_002 Withdrawal",
            	        "Guru99 returned HTTP 500");

            	throw new AssertionError(
            	        "Guru99 returned HTTP 500");
            }

            TestLogger.pass(
                    "TC_TRANS_002 Withdrawal");

        } catch (Exception e) {

            if (isHttp500Present(driver)) {

                TestLogger.blocked(
                        "TC_TRANS_002 Withdrawal",
                        "Guru99 returned HTTP 500");

                relogin(driver);
                return;
            }

            TestLogger.fail(
                    "TC_TRANS_002 Withdrawal",
                    e.getMessage());
        }
    }

    /*
     * TC_TRANS_003 : BALANCE ENQUIRY
     */
    @Test(priority = 3,description = "TC_TRANS_003 : Balance Enquiry")
    public void balanceTest() {

        WebDriver driver =
                BaseTest.getDriver();

        try {

            BalanceEnquiryPage b =
                    new BalanceEnquiryPage(driver);

            b.clickBalanceEnquiry();

            b.checkBalance(accountId);

            if (isHttp500Present(driver)) {

                TestLogger.blocked(
                        "TC_TRANS_003 Balance Enquiry",
                        "Guru99 returned HTTP 500");

                return;
            }

            TestLogger.pass(
                    "TC_TRANS_003 Balance Enquiry");

            TestLogger.print(
                   
                    "MODULE 4 STATUS : PASS ✅\n\"" +
                    "==================================================\n");

        } catch (Exception e) {

            if (isHttp500Present(driver)) {

                TestLogger.blocked(
                        "TC_TRANS_003 Balance Enquiry",
                        "Guru99 returned HTTP 500");

                return;
            }

            TestLogger.fail(
                    "TC_TRANS_003 Balance Enquiry",
                    e.getMessage());
        }
    }
}