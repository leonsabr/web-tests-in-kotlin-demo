package com.leonsabr.demo.pages.teamcity

import com.leonsabr.demo.allure.attachScreenshot
import com.leonsabr.demo.pages.KPage
import com.leonsabr.demo.uielements.teamcity.KBuildDescription
import com.leonsabr.demo.utils.waitForCondition
import com.leonsabr.demo.utils.waitForElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.allure.annotations.Step
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.Button

class KTeamCityBuildConfigurationPage(driver: WebDriver) : KPage(driver) {

    override var url = "/viewType.html?buildTypeId=%s"

    @Name("Run build button")
    @FindBy(css = "button[onclick*='runOnAgent']")
    lateinit var runBuildButton: Button

    @Name("Builds")
    lateinit var builds: List<KBuildDescription>

    @Name("Running build")
    @FindBy(css = ".running tr")
    lateinit var runningBuild: KBuildDescription

    override fun isLoaded() = builds.isNotEmpty()

    @Step
    fun runNewBuild() = apply {
        waitForElement(runBuildButton)
        runBuildButton.click()
        waitForCondition("Failed to wait for a new $runningBuild to appear on the $this page!", { runningBuild.exists() }, 60)
        attachScreenshot(runningBuild)
    }

    @Step
    fun waitForRunningBuildToFinish() = apply {
        waitForCondition("Failed to wait for $runningBuild to finish!", { !runningBuild.exists() }, 60)
    }

    @Step("Verify latest build")
    fun verifyLatestBuild(buildNumber: Int, buildStatus: String, buildUrl: String) = apply {
        builds.first().verifyBuild(buildNumber, buildStatus, buildUrl)
    }

    @Step
    fun getLatestBuildNumber() = builds.first().getBuildNumber()
}
