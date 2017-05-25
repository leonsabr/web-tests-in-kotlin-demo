package com.leonsabr.demo.rules

import com.leonsabr.demo.config.KConfig
import com.leonsabr.demo.utils.KBrowser
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.openqa.selenium.Platform
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import java.net.URL

class KBrowserHandlerRule(private val browser: KBrowser) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                browser.driver = RemoteWebDriver(URL(KConfig.seleniumHub), DesiredCapabilities(KConfig.browserName, "", Platform.ANY))
                try {
                    base.evaluate()
                } finally {
                    try {
                        browser.driver.quit()
                    } catch(ignored: WebDriverException) {
                    }
                }
            }
        }
    }
}
