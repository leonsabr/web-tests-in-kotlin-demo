package com.leonsabr.demo.uielements

import com.leonsabr.demo.utils.waitForElement
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.RemoteWebElement
import ru.yandex.qatools.allure.annotations.Step
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.Link
import ru.yandex.qatools.htmlelements.element.TypifiedElement

@Step("Verify {0} href is [{1}]")
fun Link.verifyHref(expectedHref: String) = assertThat("$name href is incorrect!", reference, equalTo(expectedHref))

@Step("Verify {0} text is [{1}]")
fun HtmlElement.verifyText(expectedText: String) {
    waitForElement(this)
    assertThat("$this text is incorrect!", text, equalTo(expectedText))
}

@Step("Verify {0} text is [{1}]")
fun TypifiedElement.verifyText(expectedText: String) {
    waitForElement(this)
    assertThat("$this text is incorrect!", text, equalTo(expectedText))
}

fun WebElement.getDriver(): WebDriver {
    val rwe = this.findElement(By.xpath("."))
    if (rwe is RemoteWebElement) {
        return rwe.wrappedDriver
    } else {
        throw UnsupportedOperationException("Can't extract driver for non-RemoteWebElement!")
    }
}
