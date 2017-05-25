package com.leonsabr.demo

import com.leonsabr.demo.allure.KAllureTestRunner
import com.leonsabr.demo.config.ADMIN
import com.leonsabr.demo.config.BUILD_CONFIGURATION_ID
import com.leonsabr.demo.config.BUILD_STATUS
import com.leonsabr.demo.config.KConfig
import com.leonsabr.demo.pages.teamcity.KTeamCityBuildConfigurationPage
import com.leonsabr.demo.pages.teamcity.KTeamCityLoginPage
import com.leonsabr.demo.rules.KBrowserHandlerRule
import com.leonsabr.demo.rules.KLoggingRule
import com.leonsabr.demo.utils.KBrowser
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.runner.RunWith
import ru.yandex.qatools.allure.annotations.Title

@RunWith(KAllureTestRunner::class)
@Title("Test in Kotlin, Other Stuff in Kotlin")
class KotlinKotlinTest {

    private val browser = KBrowser()

    @Rule @JvmField
    val rules: RuleChain = RuleChain.emptyRuleChain()
            .around(KBrowserHandlerRule(browser))
            .around(KLoggingRule(browser))

    @Test fun `Verify build run`() {
        browser.navigateTo(::KTeamCityLoginPage)
                .login(ADMIN, ADMIN)
                .waitForBuildAgent()
        var expectedBuildNumber: Int = 0
        browser.navigateTo(::KTeamCityBuildConfigurationPage, BUILD_CONFIGURATION_ID)
                .apply { expectedBuildNumber = getLatestBuildNumber() + 1 }
                .runNewBuild()
                .waitForRunningBuildToFinish()
                .verifyLatestBuild(expectedBuildNumber, BUILD_STATUS,
                        "${KConfig.teamcityBaseURL}/viewLog.html?buildId=$expectedBuildNumber&tab=buildResultsDiv&buildTypeId=$BUILD_CONFIGURATION_ID")
    }
}
