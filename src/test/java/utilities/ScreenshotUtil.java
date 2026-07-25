package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String captureScreenshot(
            WebDriver driver,
            String testName) {

        String fileName =
                testName
                + "_"
                + Thread.currentThread().getId()
                + "_"
                + System.currentTimeMillis()
                + ".png";

        String path =
                "screenshots/"
                + fileName;

        File src =
                ((TakesScreenshot) driver)
                        .getScreenshotAs(
                                OutputType.FILE);

        try {

            File dest =
                    new File(path);

            FileUtils.copyFile(
                    src,
                    dest);

            System.out.println(
                    "Screenshot Saved : "
                            + dest.getAbsolutePath());

        } catch (IOException e) {

            e.printStackTrace();
        }

        return path;
    }
}