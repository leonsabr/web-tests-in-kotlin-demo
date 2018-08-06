package com.leonsabr.demo.pages.teamcity;

import com.leonsabr.demo.pages.JPage;
import com.leonsabr.demo.uielements.teamcity.JBuildDescription;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.Button;

import java.util.List;

import static com.leonsabr.demo.allure.JAttachments.attachScreenshot;
import static com.leonsabr.demo.utils.JWaiters.exists;
import static com.leonsabr.demo.utils.JWaiters.notExists;
import static com.leonsabr.demo.utils.JWaiters.waitForCondition;
import static com.leonsabr.demo.utils.JWaiters.waitForElementExists;

public class JTeamCityBuildConfigurationPage extends JPage {

    private static final int BUILD_TIMEOUT = 60;

    @Override
    public String getUrl() {
        return "/viewType.html?buildTypeId=%s";
    }

    public JTeamCityBuildConfigurationPage(final WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "button[onclick*='runOnAgent']")
    private Button runBuildButton;

    private List<JBuildDescription> builds;

    @FindBy(css = "#running [class*='ListItem__list-item']")
    private JBuildDescription runningBuild;

    @Override
    public boolean isLoaded() {
        return !builds.isEmpty();
    }

    @Step
    public JTeamCityBuildConfigurationPage runNewBuild() {
        waitForElementExists(runBuildButton);
        runBuildButton.click();
        waitForCondition("Failed to wait for a new " + runningBuild + " to appear on the " + this + " page!",
                runningBuild, exists(), BUILD_TIMEOUT);
        attachScreenshot(runningBuild);
        return this;
    }

    @Step
    public JTeamCityBuildConfigurationPage waitForRunningBuildToFinish() {
        waitForCondition("Failed to wait for $runningBuild to finish!", runningBuild, notExists(), BUILD_TIMEOUT);
        return this;
    }

    @Step("Verify latest build")
    public JTeamCityBuildConfigurationPage verifyLatestBuild(final int buildNumber, final String buildStatus, final String buildUrl) {
        builds.get(0).verifyBuild(buildNumber, buildStatus, buildUrl);
        return this;
    }

    @Step
    public int getLatestBuildNumber() {
        return builds.get(0).getBuildNumber();
    }
}
