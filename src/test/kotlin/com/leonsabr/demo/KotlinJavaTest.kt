package com.leonsabr.demo

import com.leonsabr.demo.allure.JAllureTestRunner
import com.leonsabr.demo.config.JConfig
import com.leonsabr.demo.config.JConstants.ADMIN
import com.leonsabr.demo.config.JConstants.BUILD_CONFIGURATION_ID
import com.leonsabr.demo.config.JConstants.BUILD_STATUS
import com.leonsabr.demo.pages.teamcity.JTeamCityBuildConfigurationPage
import com.leonsabr.demo.pages.teamcity.JTeamCityLoginPage
import com.leonsabr.demo.rules.JBrowserHandlerRule
import com.leonsabr.demo.rules.JLoggingRule
import com.leonsabr.demo.utils.JBrowser
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import ru.yandex.qatools.allure.annotations.Title

@RunWith(JAllureTestRunner::class)
@Title("Test in Kotlin, Other Stuff in Java")
class KotlinJavaTest {

    private val browser = JBrowser()

    @Rule @JvmField
    val rules: RuleChain = RuleChain.emptyRuleChain()
            .around(JBrowserHandlerRule(browser))
            .around(JLoggingRule(browser))

    @Test fun `Verify build run`() {
        browser.navigateTo(JTeamCityLoginPage::class.java)
                .login(ADMIN, ADMIN)
                .waitForBuildAgent()
        val expectedBuildNumber = browser.navigateTo(JTeamCityBuildConfigurationPage::class.java, BUILD_CONFIGURATION_ID)
                .latestBuildNumber + 1
        browser.navigateTo(JTeamCityBuildConfigurationPage::class.java, BUILD_CONFIGURATION_ID)
                .runNewBuild()
                .waitForRunningBuildToFinish()
                .verifyLatestBuild(expectedBuildNumber, BUILD_STATUS,
                        "${JConfig.INSTANCE.teamcityBaseURL}/viewLog.html?buildId=$expectedBuildNumber&buildTypeId=$BUILD_CONFIGURATION_ID")
    }
}
