package com.leonsabr.demo.uielements;

import com.leonsabr.demo.utils.JWaiters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import static com.leonsabr.demo.utils.JWaiters.waitForElementExists;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class WebElementUtils {

    private WebElementUtils() {
    }

    public static WebDriver getDriver(final WebElement element) {
        final WebElement rwe = element.findElement(By.xpath("."));
        if (rwe instanceof RemoteWebElement) {
            return ((RemoteWebElement) rwe).getWrappedDriver();
        } else {
            throw new UnsupportedOperationException("Can't extract driver for non-RemoteWebElement!");
        }
    }

    @Step("Verify {0} href is [{1}]")
    public static void verifyHref(final Link link, final String expectedHref) {
        assertThat(link + " href is incorrect!", link.getReference(), equalTo(expectedHref));
    }

    @Step("Verify {0} text is [{1}]")
    public static void verifyText(final HtmlElement element, final String expectedText) {
        JWaiters.waitForElementExists(element);
        assertThat(element + " text is incorrect!", element.getText(), equalTo(expectedText));
    }

    @Step("Verify {0} text is [{1}]")
    public static void verifyText(final TypifiedElement element, final String expectedText) {
        JWaiters.waitForElementExists(element);
        assertThat(element + " text is incorrect!", element.getText(), equalTo(expectedText));
    }
}
