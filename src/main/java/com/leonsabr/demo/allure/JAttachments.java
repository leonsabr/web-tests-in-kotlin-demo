package com.leonsabr.demo.allure;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import static com.leonsabr.demo.uielements.WebElementUtils.getDriver;

public class JAttachments {

    private static final String IMAGE_PNG = "image/png";
    private static final String TEXT_PLAIN = "text/plain";

    private JAttachments() {
    }

    @Attachment(value = "{0}", type = IMAGE_PNG)
    public static byte[] attachScreenshot(@SuppressWarnings("unused") final String name, final WebDriver driver) {
        if (driver instanceof TakesScreenshot) {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } else {
            return null;
        }
    }

    public static void attachScreenshot(final WebDriver driver) {
        attachScreenshot("screenshot", driver);
    }

    @Attachment(value = "{0}", type = IMAGE_PNG)
    public static byte[] attachScreenshot(@SuppressWarnings("unused") final String name, final WebDriver driver, final WebElement element) {
        try {
            final AShot ashot = new AShot()
                    .coordsProvider(new WebDriverCoordsProvider())
                    .imageCropper(new IndentCropper().addIndentFilter(new BlurFilter()));
            BufferedImage image = ashot.takeScreenshot(driver, element).getImage();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);
            return out.toByteArray();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void attachScreenshot(final WebDriver driver, final WebElement element) {
        attachScreenshot("screenshot", driver, element);
    }

    public static void attachScreenshot(final String name, final WebElement element) {
        attachScreenshot(name, getDriver(element), element);
    }

    public static void attachScreenshot(final WebElement element) {
        attachScreenshot("screenshot", getDriver(element), element);
    }

    public static void attachScreenshot(final HtmlElement element) {
        attachScreenshot(element.getName(), element);
    }

    public static void attachScreenshot(final TypifiedElement element) {
        attachScreenshot(element.getName(), element);
    }

    @Attachment(value = "{0}", type = TEXT_PLAIN)
    public static String attachText(@SuppressWarnings("unused") final String name, final String text) {
        return text;
    }

    public static void attachText(final String text) {
        attachText("text", text);
    }

    @SuppressWarnings("unused")
    @Step("{0}")
    public void step(final String title, final Runnable code) {
        code.run();
    }
}
