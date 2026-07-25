package utilities;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private static final int DEFAULT_TIMEOUT = 10;

    public static WebElement waitForVisibility(
            WebDriver driver,
            WebElement element) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(
            WebDriver driver,
            By locator) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions
                                .visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(
            WebDriver driver,
            WebElement element) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions
                                .elementToBeClickable(element));
    }

    public static boolean waitForInvisibility(
            WebDriver driver,
            WebElement element) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions.invisibilityOf(element));
    }

    public static Alert waitForAlert(
            WebDriver driver) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions.alertIsPresent());
    }

    public static boolean waitForTitleContains(
            WebDriver driver,
            String title) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions.titleContains(title));
    }

    public static boolean waitForUrlContains(
            WebDriver driver,
            String urlPart) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(
                        ExpectedConditions.urlContains(urlPart));
    }
}