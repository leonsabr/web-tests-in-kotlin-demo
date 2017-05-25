package com.leonsabr.demo.allure

import com.leonsabr.demo.uielements.getDriver
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import ru.yandex.qatools.allure.annotations.Attachment
import ru.yandex.qatools.allure.annotations.Step
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import ru.yandex.qatools.ashot.cropper.indent.BlurFilter
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TypifiedElement
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

private const val IMAGE_PNG: String = "image/png"
private const val TEXT_PLAIN: String = "text/plain"

@Attachment(value = "{0}", type = IMAGE_PNG)
fun attachScreenshot(@Suppress("unused_parameter") name: String = "screenshot", driver: WebDriver) =
        if (driver is TakesScreenshot) {
            driver.getScreenshotAs(OutputType.BYTES)
        } else {
            null
        }

@Attachment(value = "{0}", type = IMAGE_PNG)
fun attachScreenshot(@Suppress("unused_parameter") name: String = "screenshot", driver: WebDriver, element: WebElement): ByteArray? {
    try {
        val ashot = AShot()
                .coordsProvider(WebDriverCoordsProvider())
                .imageCropper(IndentCropper().addIndentFilter(BlurFilter()))
        val image = ashot.takeScreenshot(driver, element).image
        val out = ByteArrayOutputStream()
        ImageIO.write(image, "png", out)
        return out.toByteArray()
    } catch (ignored: Exception) {
        return null
    }
}

fun attachScreenshot(name: String = "screenshot", element: WebElement) = attachScreenshot(name, element.getDriver(), element)

fun attachScreenshot(element: HtmlElement) = attachScreenshot(element.name, element)

fun attachScreenshot(element: TypifiedElement) = attachScreenshot(element.name, element)

@Attachment(value = "{0}", type = TEXT_PLAIN)
fun attachText(@Suppress("unused_parameter") name: String = "text", text: String?) = text

@Suppress("unused")
@Step("{0}")
fun step(@Suppress("unused_parameter") title: String, code: () -> Any) = code()
