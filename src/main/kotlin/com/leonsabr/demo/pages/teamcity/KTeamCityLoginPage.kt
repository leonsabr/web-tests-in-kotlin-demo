package com.leonsabr.demo.pages.teamcity

import com.leonsabr.demo.allure.attachScreenshot
import com.leonsabr.demo.pages.KPage
import com.leonsabr.demo.utils.waitForPage
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.FindBy
import ru.yandex.qatools.allure.annotations.Step
import ru.yandex.qatools.htmlelements.annotations.Name
import ru.yandex.qatools.htmlelements.element.Button
import ru.yandex.qatools.htmlelements.element.HtmlElement
import ru.yandex.qatools.htmlelements.element.TextInput

class KTeamCityLoginPage(driver: WebDriver) : KPage(driver) {

    override var url = "/login.html"

    @Name("Login form")
    @FindBy(css = "[id='loginPage']")
    lateinit var loginForm: HtmlElement

    @Name("Username input")
    @FindBy(css = "[name='username']")
    lateinit var usernameInput: TextInput

    @Name("Password input")
    @FindBy(css = "[name='password']")
    lateinit var passwordInput: TextInput

    @Name("Login button")
    @FindBy(css = "[name='submitLogin']")
    lateinit var loginButton: Button

    override fun isLoaded() = loginForm.exists()

    @Step("Log in to TeamCity as {0}/{1}")
    fun login(username: String, password: String): KTeamCityOverviewPage {
        usernameInput.sendKeys(username)
        passwordInput.sendKeys(password)
        attachScreenshot(loginForm)
        loginButton.click()
        return waitForPage(KTeamCityOverviewPage(driver), 60)
    }
}
