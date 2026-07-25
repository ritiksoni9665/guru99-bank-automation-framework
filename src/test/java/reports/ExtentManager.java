package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {

        if (extent == null) {

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(
                            "test-output/ExtentReport.html");

            spark.config().setReportName(
                    "Guru99 Automation Report");

            spark.config().setDocumentTitle(
                    "Guru99 Capstone Report");

            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo(
                    "Tester",
                    "Ritik Soni");

            extent.setSystemInfo(
                    "Framework",
                    "Selenium + TestNG");

            extent.setSystemInfo(
                    "Project",
                    "Guru99 Banking Automation");
            extent.setSystemInfo(
                    "Browser",
                    "Chrome");

            extent.setSystemInfo(
                    "Environment",
                    "QA");

            extent.setSystemInfo(
                    "Automation Tool",
                    "Selenium WebDriver");

            extent.setSystemInfo(
                    "Execution",
                    "TestNG");
        }

        return extent;
    }
}