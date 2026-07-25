package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import utilities.ExcelUtils;
import utilities.TestLogger;
import utilities.WaitUtils;

public class LoginTest extends BaseTest {

    private static boolean moduleFailed = false;

    @BeforeClass
    public void setupModule() {

        TestLogger.print(
                "\n==================================================\n" +
                "MODULE 1 : LOGIN\n" +
                "==================================================\n");
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {

        return ExcelUtils.getData(
                "src/test/java/testdata/LoginData.xlsx",
                "login");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(
            String username,
            String password,
            String expected) {

        WebDriver driver = BaseTest.getDriver();

        driver.get("https://demo.guru99.com/V4/");

        LoginPage lp = new LoginPage(driver);

        if (username != null && !username.trim().isEmpty()) {
            lp.enterUserId(username);
        }

        if (password != null && !password.trim().isEmpty()) {
            lp.enterPassword(password);
        }

        switch (expected.toUpperCase()) {

            case "VALID":

                lp.clickLogin();

                Assert.assertTrue(
                        driver.getTitle().contains("Manager"),
                        "Dashboard not opened");

                TestLogger.pass(
                        "TC_LOGIN_001 : Valid Username + Valid Password" );

                break;

            case "INVALID_USER":

                lp.clickLogin();

                Alert invalidUserAlert =
                        WaitUtils.waitForAlert(driver);

                String invalidUserMsg =
                        invalidUserAlert.getText();

                Assert.assertEquals(
                        invalidUserMsg,
                        "User or Password is not valid");

                invalidUserAlert.accept();

                TestLogger.pass(
                        "TC_LOGIN_002 : Invalid Username + Valid Password" );

                break;

            case "INVALID_PASS":

                lp.clickLogin();

                Alert invalidPassAlert =
                        WaitUtils.waitForAlert(driver);

                String invalidPassMsg =
                        invalidPassAlert.getText();

                Assert.assertEquals(
                        invalidPassMsg,
                        "User or Password is not valid");

                invalidPassAlert.accept();

                TestLogger.pass(
                        "TC_LOGIN_003 : Valid Username + Invalid Password" );

                break;

            case "INVALID_BOTH":

                lp.clickLogin();

                Alert invalidBothAlert =
                        WaitUtils.waitForAlert(driver);

                String invalidBothMsg =
                        invalidBothAlert.getText();

                Assert.assertEquals(
                        invalidBothMsg,
                        "User or Password is not valid");

                invalidBothAlert.accept();

                TestLogger.pass(
                        "TC_LOGIN_004 : Invalid Username + Invalid Password");

                break;

            case "BLANK_USER":

                WebElement userField =
                        driver.findElement(By.name("uid"));

                userField.click();

                String userValidation =
                        userField.getAttribute("validationMessage");

                Assert.assertNotNull(userValidation);

                TestLogger.pass(
                        "TC_LOGIN_005 : Empty Username + Valid Password");

                break;

            case "BLANK_PASS":

                WebElement passField =
                        driver.findElement(By.name("password"));

                passField.click();

                String passValidation =
                        passField.getAttribute("validationMessage");

                Assert.assertNotNull(passValidation);

                TestLogger.pass(
                        "TC_LOGIN_006 : Valid Username + Empty Password");

                break;

            default:

                moduleFailed = true;

                Assert.fail(
                        "Unknown expected value in Excel: "
                                + expected);
        }
    }

    @AfterClass
    public void endModule() {

        if (moduleFailed) {

            TestLogger.print(
                    "MODULE 1 STATUS : FAIL ❌\n" +
                    "===============================================================\n");
        }

        else {

            TestLogger.print(
                    "MODULE 1 STATUS : PASS ✅\n" +
                    "===============================================================\n");
        }
    }
}