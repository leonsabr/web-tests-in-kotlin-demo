package com.leonsabr.demo;

import com.leonsabr.demo.allure.KAllureTestRunner;
import com.leonsabr.demo.config.KConfig;
import com.leonsabr.demo.pages.teamcity.KTeamCityBuildConfigurationPage;
import com.leonsabr.demo.pages.teamcity.KTeamCityLoginPage;
import com.leonsabr.demo.rules.KBrowserHandlerRule;
import com.leonsabr.demo.rules.KLoggingRule;
import com.leonsabr.demo.utils.KBrowser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import ru.yandex.qatools.allure.annotations.Title;

import static com.leonsabr.demo.config.KConstantsKt.ADMIN;
import static com.leonsabr.demo.config.KConstantsKt.BUILD_CONFIGURATION_ID;
import static com.leonsabr.demo.config.KConstantsKt.BUILD_STATUS;

@RunWith(KAllureTestRunner.class)
@Title("Test in Java, Other Stuff in Kotlin")
public class JavaKotlinTest {

    private final KBrowser browser = new KBrowser();

    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new KBrowserHandlerRule(browser))
            .around(new KLoggingRule(browser, false));

    @Title("Verify build run")
    @Test
    public void verifyBuildRun() {
        browser.navigateTo(KTeamCityLoginPage::new)
                .login(ADMIN, ADMIN)
                .waitForBuildAgent();
        final int expectedBuildNumber = browser.navigateTo(KTeamCityBuildConfigurationPage::new, BUILD_CONFIGURATION_ID)
                .getLatestBuildNumber() + 1;
        browser.navigateTo(KTeamCityBuildConfigurationPage::new, BUILD_CONFIGURATION_ID)
                .runNewBuild()
                .waitForRunningBuildToFinish()
                .verifyLatestBuild(expectedBuildNumber, BUILD_STATUS,
                        String.format("%s/viewLog.html?buildId=%d&buildTypeId=%s",
                                KConfig.INSTANCE.getTeamcityBaseURL(), expectedBuildNumber, BUILD_CONFIGURATION_ID));
    }
}
