package com.leonsabr.demo.pages.teamcity;

import com.leonsabr.demo.allure.JAttachments;
import com.leonsabr.demo.pages.JPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.annotations.Name;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextInput;

import static com.leonsabr.demo.allure.JAttachments.attachScreenshot;
import static com.leonsabr.demo.utils.JWaiters.waitForPage;

public class JTeamCityLoginPage extends JPage {

    @Override
    public String getUrl() {
        return "/login.html";
    }

    public JTeamCityLoginPage(final WebDriver driver) {
        super(driver);
    }

    @Name("Login form")
    @FindBy(css = "[id='loginPage']")
    private HtmlElement loginForm;

    @Name("Username input")
    @FindBy(css = "[name='username']")
    private TextInput usernameInput;

    @Name("Password input")
    @FindBy(css = "[name='password']")
    private TextInput passwordInput;

    @Name("Login button")
    @FindBy(css = "[name='submitLogin']")
    private Button loginButton;

    @Override
    public boolean isLoaded() {
        return loginForm.exists();
    }

    @Step("Log in to TeamCity as {0}/{1}")
    public JTeamCityOverviewPage login(final String username, final String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        JAttachments.attachScreenshot(loginForm);
        loginButton.click();
        return waitForPage(new JTeamCityOverviewPage(driver), 60);
    }
}
