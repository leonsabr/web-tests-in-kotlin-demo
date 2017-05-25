package com.leonsabr.demo.utils

import com.leonsabr.demo.pages.KPage
import org.junit.Assert.fail
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TypifiedElement
import java.util.concurrent.TimeUnit

fun waitForCondition(message: String, condition: () -> Boolean, timeoutInSeconds: Long = 30, intervalInMillis: Long = 500) {
    val deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeoutInSeconds)

    while (System.currentTimeMillis() < deadline) {
        if (condition()) return
        try {
            Thread.sleep(intervalInMillis)
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
            throw RuntimeException(e)
        }
    }

    fail(message)
}

fun <T : KPage> waitForPage(page: T, timeoutInSeconds: Long = 15): T {
    waitForCondition("Failed to wait for $page to load!", { page.isLoaded() }, timeoutInSeconds)
    return page
}

fun waitForElement(element: HtmlElement, timeoutInSeconds: Long = 15) =
        waitForCondition("Can't find $element!", { element.exists() }, timeoutInSeconds)

fun waitForElement(element: TypifiedElement, timeoutInSeconds: Long = 15) =
        waitForCondition("Can't find $element!", { element.exists() }, timeoutInSeconds)
