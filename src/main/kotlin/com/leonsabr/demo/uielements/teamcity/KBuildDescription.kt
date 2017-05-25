package com.leonsabr.demo.uielements.teamcity

import com.leonsabr.demo.allure.attachScreenshot
import com.leonsabr.demo.allure.attachText
import com.leonsabr.demo.uielements.verifyHref
import com.leonsabr.demo.uielements.verifyText
import com.leonsabr.demo.utils.waitForElement
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.Link

const val BUILD_NUMBER_PREFIX = "#"

@Name("Build description")
@FindBy(css = ".historyList tbody tr")
class KBuildDescription : HtmlElement() {

    @Name("Build number")
    @FindBy(css = ".buildNumber")
    lateinit var buildNumberText: HtmlElement

    @Name("Build results link")
    @FindBy(css = ".status a.resultsLink")
    lateinit var buildResultsLink: Link

    fun verifyBuild(buildNumber: Int, buildStatus: String, buildUrl: String) = apply {
        attachScreenshot("Build ${addPrefix(buildNumber)}", this)
        buildNumberText.verifyText(addPrefix(buildNumber))
        buildResultsLink.verifyText(buildStatus)
        buildResultsLink.verifyHref(buildUrl)
    }

    fun getBuildNumber(): Int {
        waitForElement(buildNumberText)
        val buildNumber = buildNumberText.text.removePrefix(BUILD_NUMBER_PREFIX)
        attachText("Build number", buildNumber)
        return buildNumber.toInt()
    }

    private fun addPrefix(buildNumber: Int) = BUILD_NUMBER_PREFIX + buildNumber
}
