package com.leonsabr.demo.pages.teamcity;

import com.leonsabr.demo.pages.JPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

import static com.leonsabr.demo.utils.JWaiters.waitForCondition;

public class JTeamCityOverviewPage extends JPage {

    @Override
    public String getUrl() {
        return "/overview.html";
    }

    public JTeamCityOverviewPage(final WebDriver driver) {
        super(driver);
    }

    @Name("Configure Visible Projects link")
    @FindBy(css = "[id='configVisibleProjects']")
    private Link configureVisibleProjectsLink;

    @Name("Number of build agents")
    @FindBy(css = "#agents_Tab .tabCounter")
    private HtmlElement numberOfBuildAgents;

    @Override
    public boolean isLoaded() {
        return configureVisibleProjectsLink.exists() && numberOfBuildAgents.exists();
    }

    @Step
    public JTeamCityOverviewPage waitForBuildAgent() {
        waitForCondition("Failed to wait for build agent to register in TeamCity!",
                numberOfBuildAgents, el -> Integer.parseInt(el.getText()) > 0, 180);
        return this;
    }
}
