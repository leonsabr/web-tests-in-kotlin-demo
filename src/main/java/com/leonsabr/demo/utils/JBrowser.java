package com.leonsabr.demo.utils;

import com.leonsabr.demo.allure.JAttachments;
import com.leonsabr.demo.config.JConfig;
import com.leonsabr.demo.pages.JPage;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;

import static com.leonsabr.demo.allure.JAttachments.attachScreenshot;
import static com.leonsabr.demo.utils.JWaiters.waitForPage;

public class JBrowser {

    private WebDriver driver;

    public void setDriver(final WebDriver driver) {
        this.driver = driver;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public <T extends JPage> T navigateTo(final Class<T> clazz, final String... params) {
        T page = null;
        try {
            page = clazz.getConstructor(WebDriver.class).newInstance(driver);
        } catch (Exception ignored) {
            //not gonna happen
        }
        return navigateTo(page, params);
    }

    private  <T extends JPage> T navigateTo(final T page, final String... params) {
        return loadPage(page, JConfig.INSTANCE.getTeamcityBaseURL() + String.format(page.getUrl(), (Object[]) params));
    }

    @Step("Open {1}")
    private <T extends JPage> T loadPage(final T page, final String url) {
        driver.get(url);
        JWaiters.waitForPage(page);
        JAttachments.attachScreenshot(page.toString(), driver);
        return page;
    }
}
