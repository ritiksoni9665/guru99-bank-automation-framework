package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();

            driver.set(new ChromeDriver());
        }

        else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();

            driver.set(new EdgeDriver());
        }

        else if (browser.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();

            driver.set(new FirefoxDriver());
        }

        else {

            throw new RuntimeException(
                    "Unsupported Browser : " + browser);
        }

        getDriver()
                .manage()
                .window()
                .maximize();
    }

    public static WebDriver getDriver() {

        return driver.get();
    }

    @AfterClass
    public void tearDown() {

        if (getDriver() != null) {

            getDriver().quit();
            driver.remove();
        }
    }
}