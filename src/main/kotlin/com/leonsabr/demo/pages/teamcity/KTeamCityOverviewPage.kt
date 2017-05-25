package com.leonsabr.demo.pages.teamcity

import com.leonsabr.demo.pages.KPage
import com.leonsabr.demo.utils.waitForCondition
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.allure.annotations.Step
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.Link

class KTeamCityOverviewPage(driver: WebDriver) : KPage(driver) {

    override var url = "/overview.html"

    @Name("Configure Visible Projects link")
    @FindBy(css = "[id='configVisibleProjects']")
    lateinit var configureVisibleProjectsLink: Link

    @Name("Number of build agents")
    @FindBy(css = "#agents_Tab .tabCounter")
    lateinit var numberOfBuildAgents: HtmlElement

    override fun isLoaded() = configureVisibleProjectsLink.exists() && numberOfBuildAgents.exists()

    @Step
    fun waitForBuildAgent() = apply {
        waitForCondition("Failed to wait for build agent to register in TeamCity!", { numberOfBuildAgents.text.toInt() > 0 }, 180)
    }
}
