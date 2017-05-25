package com.leonsabr.demo.rules

import com.leonsabr.demo.allure.attachScreenshot
import com.leonsabr.demo.allure.attachText
import com.leonsabr.demo.utils.KBrowser
import org.junit.AssumptionViolatedException
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class KLoggingRule(private val browser: KBrowser, private var needLogs: Boolean = false) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    base.evaluate()
                } catch (e: Throwable) {
                    needLogs = e !is AssumptionViolatedException
                    throw e
                } finally {
                    if (needLogs) {
                        attachScreenshot("Screenshot of the failure", browser.driver)
                        attachText("The last URL in browser", browser.driver.currentUrl)
                    }
                }
            }
        }
    }
}
