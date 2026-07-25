package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddCustomerPage;
import pages.LoginPage;
import pages.NewAccountPage;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.TestLogger;
import utilities.WaitUtils;

public class NewAccountTest extends BaseTest {

    private static String customerId;
    private static boolean moduleFailed = false;

    @BeforeClass
    public void startModule() {

        TestLogger.print(
                "\n==================================================\n" +
                "MODULE 3 : ACCOUNT MANAGEMENT\n" +
                "==================================================\n");

        WebDriver driver = BaseTest.getDriver();

        ConfigReader config = new ConfigReader();

        driver.get(config.getUrl());

        LoginPage lp = new LoginPage(driver);

        lp.login(
                config.getUsername(),
                config.getPassword());

        Object[][] customerData =
                ExcelUtils.getData(
                        "src/test/java/testdata/CustomerData.xlsx",
                        "Customer");

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        String email =
                "ritik" + System.currentTimeMillis() + "@gmail.com";

        customer.addCustomer(
                customerData[0][0].toString(),
                customerData[0][1].toString(),
                customerData[0][2].toString(),
                customerData[0][3].toString(),
                customerData[0][4].toString(),
                customerData[0][5].toString(),
                customerData[0][6].toString(),
                email,
                customerData[0][7].toString());

        customerId = customer.getCustomerId();

        TestLogger.print("Customer ID : " + customerId);
    }

    @DataProvider(name = "accountData")
    public Object[][] accountData() {

        return ExcelUtils.getData(
                "src/test/java/testdata/AccountData.xlsx",
                "Account");
    }

    @Test(dataProvider = "accountData")
    public void newAccountTest(
            String accountType,
            String deposit,
            String expected) {

        WebDriver driver = BaseTest.getDriver();

        NewAccountPage account =
                new NewAccountPage(driver);

        account.clickNewAccount();

        if (expected.equalsIgnoreCase("SAVINGS")) {

            account.createAccount(
                    customerId,
                    accountType,
                    deposit);

            Assert.assertTrue(
                    driver.getPageSource()
                            .contains("Account Generated Successfully"));

            TestLogger.pass(
                    "TC_NA_01 : Create Savings Account");
        }

        else if (expected.equalsIgnoreCase("CURRENT")) {

            account.createAccount(
                    customerId,
                    accountType,
                    deposit);

            Assert.assertTrue(
                    driver.getPageSource()
                            .contains("Account Generated Successfully"));

            TestLogger.pass(
                    "TC_NA_02 : Create Current Account");
        }

        else if (expected.equalsIgnoreCase("INVALID_CUSTOMER")) {

            account.createAccount(
                    "99999999",
                    accountType,
                    deposit);

            Alert alert =
                    WaitUtils.waitForAlert(driver);

            Assert.assertEquals(
                    alert.getText(),
                    "Customer does not exist!!");

            alert.accept();

            TestLogger.pass(
                    "TC_NA_03 : Invalid Customer ID");
        }

        else if (expected.equalsIgnoreCase("BLANK_DEPOSIT")) {

            account.createAccount(
                    customerId,
                    accountType,
                    "");

            Assert.assertTrue(
                    driver.getPageSource()
                          .contains("Initial Deposit must not be blank"));

            TestLogger.pass(
                    "TC_NA_04 : Blank Initial Deposit");
        }

        else {

            moduleFailed = true;

            Assert.fail(
                    "Unknown Expected Value : "
                            + expected);
        }
    }

    @AfterClass
    public void endModule() {

        if (moduleFailed) {

            TestLogger.print(
                    "MODULE 3 STATUS : FAIL ❌\n" +
                    "===============================================================\n");
        }

        else {

            TestLogger.print(
                    "MODULE 3 STATUS : PASS ✅\n" +
                    "===============================================================\n");
        }
    }
}