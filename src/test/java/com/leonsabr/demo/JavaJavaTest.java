package com.leonsabr.demo;

import com.leonsabr.demo.allure.JAllureTestRunner;
import com.leonsabr.demo.config.JConfig;
import com.leonsabr.demo.config.JConstants;
import com.leonsabr.demo.pages.teamcity.JTeamCityBuildConfigurationPage;
import com.leonsabr.demo.pages.teamcity.JTeamCityLoginPage;
import com.leonsabr.demo.rules.JBrowserHandlerRule;
import com.leonsabr.demo.rules.JLoggingRule;
import com.leonsabr.demo.utils.JBrowser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;
import ru.yandex.qatools.allure.annotations.Title;

@RunWith(JAllureTestRunner.class)
@Title("Test in Java, Other Stuff in Java")
public class JavaJavaTest {

    private final JBrowser browser = new JBrowser();

    @Rule
    public RuleChain rules = RuleChain.emptyRuleChain()
            .around(new JBrowserHandlerRule(browser))
            .around(new JLoggingRule(browser));

    @Title("Verify build run")
    @Test
    public void verifyBuildRun() {
        browser.navigateTo(JTeamCityLoginPage.class)
                .login(JConstants.ADMIN, JConstants.ADMIN)
                .waitForBuildAgent();
        final int expectedBuildNumber = browser.navigateTo(JTeamCityBuildConfigurationPage.class, JConstants.BUILD_CONFIGURATION_ID)
                .getLatestBuildNumber() + 1;
        browser.navigateTo(JTeamCityBuildConfigurationPage.class, JConstants.BUILD_CONFIGURATION_ID)
                .runNewBuild()
                .waitForRunningBuildToFinish()
                .verifyLatestBuild(expectedBuildNumber, JConstants.BUILD_STATUS,
                        String.format("%s/viewLog.html?buildId=%d&tab=buildResultsDiv&buildTypeId=%s",
                                JConfig.INSTANCE.getTeamcityBaseURL(), expectedBuildNumber, JConstants.BUILD_CONFIGURATION_ID));
    }
}
