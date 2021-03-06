package com.leonsabr.demo.uielements.teamcity;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

import static com.leonsabr.demo.allure.JAttachments.attachScreenshot;
import static com.leonsabr.demo.allure.JAttachments.attachText;
import static com.leonsabr.demo.uielements.WebElementUtils.verifyHref;
import static com.leonsabr.demo.uielements.WebElementUtils.verifyText;
import static com.leonsabr.demo.utils.JWaiters.waitForElementExists;

@FindBy(css = "#shortHistory [class*='ListItem__list-item']")
public class JBuildDescription extends HtmlElement {

    private static final String BUILD_NUMBER_PREFIX = "#";

    @FindBy(css = "span[title*='Build number']")
    private HtmlElement buildNumberText;

    @FindBy(css = "a[class*='BuildStatusLink']")
    private Link buildResultsLink;

    public JBuildDescription verifyBuild(final int buildNumber, final String buildStatus, final String buildUrl) {
        attachScreenshot("Build " + addPrefix(buildNumber), this);
        verifyText(buildNumberText, addPrefix(buildNumber));
        verifyText(buildResultsLink, buildStatus);
        verifyHref(buildResultsLink, buildUrl);
        return this;
    }

    public int getBuildNumber() {
        waitForElementExists(buildNumberText);
        final String buildNumber = buildNumberText.getText().replaceFirst(BUILD_NUMBER_PREFIX, "");
        attachText("Build number", buildNumber);
        return Integer.parseInt(buildNumber);
    }

    private String addPrefix(final int buildNumber) {
        return BUILD_NUMBER_PREFIX + buildNumber;
    }
}
