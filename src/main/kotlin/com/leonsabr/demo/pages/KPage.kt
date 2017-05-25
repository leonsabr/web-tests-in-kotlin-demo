package com.leonsabr.demo.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory

abstract class KPage(val driver: WebDriver) {

    open lateinit var url: String

    abstract fun isLoaded(): Boolean

    override fun toString(): String = javaClass.simpleName

    init {
        @Suppress("LeakingThis")
        PageFactory.initElements(HtmlElementDecorator(HtmlElementLocatorFactory(driver)), this)
    }
}
