package reports;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import base.BaseTest;
import utilities.ScreenshotUtil;

public class ExtentListener implements ITestListener {

    private static ExtentReports extent =
            ExtentManager.getInstance();

    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    private static int failedTests = 0;
    private static int skippedTests = 0;

    @Override
    public void onTestStart(
            ITestResult result) {

        String className =
                result.getTestClass()
                      .getRealClass()
                      .getSimpleName();

        String testName =
                result.getMethod()
                      .getMethodName();

        /*
         * MODULE 1 : LOGIN
         */
        if (className.equals("LoginTest")) {

            String expected =
                    result.getParameters()[2].toString();

            switch (expected.toUpperCase()) {

                case "VALID":
                    testName = "TC_LOGIN_001 : Valid Username + Valid Password";
                    break;

                case "INVALID_USER":
                    testName = "TC_LOGIN_002 : Invalid Username + Valid Password";
                    break;

                case "INVALID_PASS":
                    testName = "TC_LOGIN_003 : Valid Username + Invalid Password";
                    break;

                case "INVALID_BOTH":
                    testName = "TC_LOGIN_004 : Invalid Username + Invalid Password";
                    break;

                case "BLANK_USER":
                    testName = "TC_LOGIN_005 : Empty Username + Valid Password";
                    break;

                case "BLANK_PASS":
                    testName = "TC_LOGIN_006 : Valid Username + Empty Password";
                    break;
            }
        }

        /*
         * MODULE 3 : ACCOUNT MANAGEMENT
         */
        else if (className.equals("NewAccountTest")) {

            String expected =
                    result.getParameters()[2].toString();

            switch (expected.toUpperCase()) {

                case "SAVINGS":
                    testName = "TC_NA_01 : Create Savings Account";
                    break;

                case "CURRENT":
                    testName = "TC_NA_02 : Create Current Account";
                    break;

                case "INVALID_CUSTOMER":
                    testName = "TC_NA_03 : Invalid Customer ID";
                    break;

                case "BLANK_DEPOSIT":
                    testName = "TC_NA_04 : Blank Initial Deposit";
                    break;
            }
        }

        /*
         * MODULE 4 : TRANSACTIONS
         */
        else if (className.equals("TransactionTest")) {

            testName =
                    result.getMethod()
                          .getDescription();
        }

        /*
         * MODULE 5 : FUND TRANSFER
         */
        else if (className.equals("FundTransferTest")) {

            String scenario =
                    result.getParameters()[10].toString();

            switch (scenario.toUpperCase()) {

                case "VALID":
                    testName = "TC_FT_001 : Valid Transfer";
                    break;

                case "INSUFFICIENT":
                    testName = "TC_FT_002 : Insufficient Balance";
                    break;

                case "INVALID":
                    testName = "TC_FT_003 : Invalid Account";
                    break;
            }
        }

        ExtentTest extentTest =
                extent.createTest(testName);

        if (className.contains("Login")) {

            extentTest.assignCategory(
                    "Module 1 - Login");
        }

        else if (className.contains("Customer")) {

            extentTest.assignCategory(
                    "Module 2 - Customer Creation");
        }

        else if (className.contains("Account")) {

            extentTest.assignCategory(
                    "Module 3 - Account Management");
        }

        else if (className.equals("TransactionTest")) {

            extentTest.assignCategory(
                    "Module 4 - Transactions");
        }

        else if (className.contains("FundTransfer")) {

            extentTest.assignCategory(
                    "Module 5 - Fund Transfer");
        }

        test.set(extentTest);
    }
    @Override
    public void onTestSuccess(
            ITestResult result) {

        test.get().log(
                Status.PASS,
                "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(
            ITestResult result) {

        failedTests++;

        System.out.println(
                "\n❌ FAILED TEST : "
                        + result.getName());

        System.out.println(
                "Reason : "
                        + result.getThrowable());

        test.get().log(
                Status.FAIL,
                "Test Failed");

        test.get().fail(
                result.getThrowable());

        try {

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            BaseTest.getDriver(),
                            result.getName());

            test.get()
                .addScreenCaptureFromPath(
                        screenshotPath);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(
            ITestResult result) {

        skippedTests++;

        test.get().log(
                Status.SKIP,
                "Test Skipped");
    }
    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        int passedTests = context.getPassedTests().size();
        int failedTests = context.getFailedTests().size();
        int skippedTests = context.getSkippedTests().size();

        int totalTests = passedTests + failedTests + skippedTests;

        System.out.println("\n==================================================");
        System.out.println("FINAL EXECUTION SUMMARY");
        System.out.println("==================================================");

        System.out.println("\nModules Executed : 5");

        System.out.println("\nTotal Test Cases : " + totalTests);

        System.out.println("\nPassed           : " + passedTests + " ✅");
        System.out.println("Failed           : " + failedTests + (failedTests > 0 ? " ❌" : ""));
        System.out.println("Skipped          : " + skippedTests);

        if (failedTests == 0) {
            System.out.println("\nExecution Status : SUCCESS ✅");
        } else {
            System.out.println("\nExecution Status : FAILED ❌");
        }

        System.out.println("\n==================================================");
    }
    
}