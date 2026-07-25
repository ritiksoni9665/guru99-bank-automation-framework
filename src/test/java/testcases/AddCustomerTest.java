package testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddCustomerPage;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.ExcelUtils;
import utilities.TestLogger;

public class AddCustomerTest extends BaseTest {

    private static String customerId;
    private static String usedEmail;

    private static String testName;
    private static String testDob;
    private static String testAddress;
    private static String testCity;
    private static String testState;
    private static String testPin;
    private static String testMobile;
    private static String testPassword;

    @BeforeClass
    public void startModule() {

        TestLogger.print(
                "\n==================================================\n" +
                "MODULE 2 : CUSTOMER CREATION\n" +
                "==================================================\n");
    }

    @DataProvider(name = "customerData")
    public Object[][] customerData() {

        return ExcelUtils.getData(
                "src/test/java/testdata/CustomerData.xlsx",
                "Customer");
    }

    /*
     * TC_AC_01
     */
    @Test(priority = 1, dataProvider = "customerData", description = "TC_AC_01 : Add Valid Customer")
    public void addValidCustomer(
            String name,
            String dob,
            String address,
            String city,
            String state,
            String pin,
            String mobile,
            String password) {

        WebDriver driver = BaseTest.getDriver();

        ConfigReader config = new ConfigReader();

        driver.get(config.getUrl());

        LoginPage lp = new LoginPage(driver);

        lp.login(
                config.getUsername(),
                config.getPassword());

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        usedEmail =
                "ritik" +
                System.currentTimeMillis() +
                "@gmail.com";

        testName = name;
        testDob = dob;
        testAddress = address;
        testCity = city;
        testState = state;
        testPin = pin;
        testMobile = mobile;
        testPassword = password;

        customer.addCustomer(
                name,
                dob,
                address,
                city,
                state,
                pin,
                mobile,
                usedEmail,
                password);

        Assert.assertTrue(
                driver.getPageSource()
                        .contains("Customer Registered Successfully"));

        customerId = customer.getCustomerId();

        TestLogger.pass(
                "TC_AC_01 Add Valid Customer");
    }

    /*
     * TC_AC_02
     */
    @Test(priority = 2,
          dependsOnMethods = "addValidCustomer", description = "TC_AC_02 : Validate Customer ID")
    public void validateCustomerId() {

        Assert.assertNotNull(customerId);

        Assert.assertFalse(customerId.isEmpty());

        Assert.assertTrue(customerId.matches("\\d+"));

        TestLogger.pass(
                "TC_AC_02 Validate Customer ID");
    }

    /*
     * TC_AC_03
     */
    @Test(priority = 3,description = "TC_AC_03 : Blank Name Field")
    public void blankNameField() {

        WebDriver driver = BaseTest.getDriver();

        ConfigReader config = new ConfigReader();

        driver.get(config.getUrl());

        LoginPage lp = new LoginPage(driver);

        lp.login(
                config.getUsername(),
                config.getPassword());

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        customer.clickSubmit();

        try {

            org.openqa.selenium.Alert alert =
                    driver.switchTo().alert();

            String alertText =
                    alert.getText();

            Assert.assertTrue(
                    alertText.toLowerCase()
                            .contains("fill"));

            alert.accept();

        } catch (Exception e) {

            Assert.assertTrue(
                    customer.getNameValidationMessage()
                            .contains("must not be blank"));
        }

        TestLogger.pass(
                "TC_AC_03 Blank Name Field");
    }

    /*
     * TC_AC_04
     */
    @Test(priority = 4,description = "TC_AC_04 : Invalid PIN Format")
    public void invalidPinFormat() {

        WebDriver driver = BaseTest.getDriver();

        ConfigReader config = new ConfigReader();

        driver.get(config.getUrl());

        LoginPage lp = new LoginPage(driver);

        lp.login(
                config.getUsername(),
                config.getPassword());

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        customer.enterPin("ABC12");

        Assert.assertTrue(
                customer.getPinValidationMessage()
                        .length() > 0);

        TestLogger.pass(
                "TC_AC_04 Invalid PIN Format");
    }

    /*
     * TC_AC_05
     */
    @Test(priority = 5,
          dependsOnMethods = "addValidCustomer",description = "TC_AC_05 : Duplicate Email Validation")
    public void duplicateEmailValidation() {

        WebDriver driver = BaseTest.getDriver();

        ConfigReader config = new ConfigReader();

        driver.get(config.getUrl());

        LoginPage lp = new LoginPage(driver);

        lp.login(
                config.getUsername(),
                config.getPassword());

        AddCustomerPage customer =
                new AddCustomerPage(driver);

        customer.clickNewCustomer();

        customer.addCustomer(
                testName,
                testDob,
                testAddress,
                testCity,
                testState,
                testPin,
                testMobile,
                usedEmail,
                testPassword);

        org.openqa.selenium.Alert alert =
                driver.switchTo().alert();

        String alertText =
                alert.getText();

        Assert.assertTrue(
                alertText.contains("Email Address Already Exist"),
                "Duplicate email alert not displayed.");

        alert.accept();

        TestLogger.pass(
                "TC_AC_05 Duplicate Email Validation");
    }

    @AfterClass
    public void endModule() {

        TestLogger.print(
                "MODULE 2 STATUS : PASS ✅\n" +
                "===============================================================\n");
    }
}