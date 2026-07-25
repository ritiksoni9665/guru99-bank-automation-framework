package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddCustomerPage;
import pages.FundTransferPage;
import pages.LoginPage;
import pages.NewAccountPage;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.TestLogger;
import utilities.WaitUtils;

public class FundTransferTest extends BaseTest {

    private static boolean modulePrinted = false;
    private static boolean moduleFailed = false;

    @BeforeClass
    public void startModule() {

        if (!modulePrinted) {

            TestLogger.print(
                    "\n==================================================\n" +
                    "MODULE 5 : FUND TRANSFER\n" +
                    "==================================================\n");

            modulePrinted = true;
        }
    }

    @DataProvider(name = "transferData")
    public Object[][] transferData() {

        Object[][] customerData =
                ExcelUtils.getData(
                        "src/test/java/testdata/CustomerData.xlsx",
                        "Customer");

        Object[][] transferData =
                ExcelUtils.getData(
                        "src/test/java/testdata/TransferData.xlsx",
                        "Transfer");

        Object[][] finalData =
                new Object[transferData.length][11];

        for (int i = 0; i < transferData.length; i++) {

            for (int j = 0; j < 8; j++) {
                finalData[i][j] = customerData[0][j];
            }

            finalData[i][8] = transferData[i][0];
            finalData[i][9] = transferData[i][1];
            finalData[i][10] = transferData[i][2];
        }

        return finalData;
    }

    @Test(priority = 1, dataProvider = "transferData")
    public void validTransfer(

            String name,
            String dob,
            String address,
            String city,
            String state,
            String pin,
            String mobile,
            String password,

            String amount,
            String description,
            String scenario) {

        try {

            WebDriver driver = BaseTest.getDriver();
            ConfigReader config = new ConfigReader();

            driver.get(config.getUrl());

            LoginPage login = new LoginPage(driver);

            login.login(
                    config.getUsername(),
                    config.getPassword());

            AddCustomerPage customer =
                    new AddCustomerPage(driver);

            customer.clickNewCustomer();

            String email =
                    "ritik" + System.currentTimeMillis()
                            + "@gmail.com";

            customer.addCustomer(
                    name,
                    dob,
                    address,
                    city,
                    state,
                    pin,
                    mobile,
                    email,
                    password);

            Assert.assertTrue(
                    driver.getPageSource()
                            .contains(
                                    "Customer Registered Successfully"));

            String customerId =
                    customer.getCustomerId();

            NewAccountPage account =
                    new NewAccountPage(driver);

            account.clickNewAccount();

            account.createAccount(
                    customerId,
                    "Savings",
                    "10000");

            String sourceAccount =
                    account.getAccountId();

            account.clickNewAccount();

            account.createAccount(
                    customerId,
                    "Current",
                    "5000");

            String destinationAccount =
                    account.getAccountId();

            if (scenario.equalsIgnoreCase("INVALID")) {

                destinationAccount = "99999999";
            }

            FundTransferPage transfer =
                    new FundTransferPage(driver);

            transfer.clickFundTransfer();

            transfer.transferFunds(
                    sourceAccount,
                    destinationAccount,
                    amount,
                    description);
            /*
             * ALERT HANDLING
             */
            try {

                Alert alert =
                        WaitUtils.waitForAlert(driver);

                String alertText =
                        alert.getText();

                alert.accept();

                if (scenario.equalsIgnoreCase(
                        "INSUFFICIENT")) {

                    Assert.assertTrue(
                            alertText.length() > 0);

                    TestLogger.pass(
                            "TC_FT_002 Insufficient Balance");

                    return;
                }

                if (scenario.equalsIgnoreCase(
                        "INVALID")) {

                    Assert.assertTrue(
                            alertText.length() > 0);

                    TestLogger.pass(
                            "TC_FT_003 Invalid Account");

                    return;
                }

            } catch (TimeoutException e) {

                // No Alert Appeared
            }

            /*
             * VALID TRANSFER
             */
            if (scenario.equalsIgnoreCase(
                    "VALID")) {

                Assert.assertTrue(
                        driver.getPageSource()
                                .contains(
                                        "Fund Transfer Details"),
                        "Fund Transfer Failed");

                TestLogger.print(
                        "Source Account      : "
                                + sourceAccount);

                TestLogger.print(
                        "Destination Account : "
                                + destinationAccount);

                TestLogger.print(
                        "Amount              : "
                                + amount);

                TestLogger.pass(
                        "TC_FT_001 Valid Transfer");

                return;
            }

            /*
             * INSUFFICIENT BALANCE
             */
            if (scenario.equalsIgnoreCase(
                    "INSUFFICIENT")) {

                Assert.assertTrue(
                        !driver.getPageSource()
                                .contains(
                                        "Fund Transfer Details"));

                TestLogger.pass(
                        "TC_FT_002 Insufficient Balance");

                return;
            }

            /*
             * INVALID ACCOUNT
             */
            String pageSource = driver.getPageSource();

            if (pageSource.contains("HTTP ERROR 500")
                    || pageSource.contains("This page isn't working")) {

                TestLogger.blocked(
                        "TC_FT_001 Valid Transfer",
                        "Guru99 returned HTTP 500");

                return;
            }
            if (scenario.equalsIgnoreCase(
                    "INVALID")) {

                Assert.assertTrue(
                        !driver.getPageSource()
                                .contains(
                                        "Fund Transfer Details"));

                TestLogger.pass(
                        "TC_FT_003 Invalid Account");

                return;
            }

        } catch (Exception e) {

            moduleFailed = true;
            throw e;
        }
    }

    @AfterClass
    public void endModule() {

        if (moduleFailed) {

            TestLogger.print(
                    "MODULE 5 STATUS : FAIL ❌\n" +
                    "===============================================================\n");

        } else {

            TestLogger.print(
                    "MODULE 5 STATUS : PASS ✅\n" +
                    "===============================================================\n");
        }
    }
}