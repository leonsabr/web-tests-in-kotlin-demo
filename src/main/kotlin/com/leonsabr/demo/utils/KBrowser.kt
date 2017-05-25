package com.leonsabr.demo.utils

import com.leonsabr.demo.allure.attachScreenshot
import com.leonsabr.demo.config.KConfig
import com.leonsabr.demo.pages.KPage
import org.openqa.selenium.WebDriver
import ru.yandex.qatools.allure.annotations.Step

class KBrowser {

    lateinit var driver: WebDriver

    fun <T : KPage> navigateTo(factory: (driver: WebDriver) -> T, vararg params: String) = navigateTo(factory(driver), *params)

    private fun <T : KPage> navigateTo(page: T, vararg params: String): T {
        @Step("Open {0}") fun loadPage(url: String): T {
            driver.get(url)
            waitForPage(page)
            attachScreenshot("$page", driver)
            return page
        }

        return loadPage(KConfig.teamcityBaseURL + String.format(page.url, *params))
    }
}
