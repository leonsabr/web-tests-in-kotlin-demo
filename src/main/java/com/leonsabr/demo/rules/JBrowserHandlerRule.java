package com.leonsabr.demo.rules;

import com.leonsabr.demo.config.JConfig;
import com.leonsabr.demo.utils.JBrowser;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class JBrowserHandlerRule implements TestRule {

    private final JBrowser browser;

    public JBrowserHandlerRule(final JBrowser browser) {
        this.browser = browser;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                browser.setDriver(new RemoteWebDriver(new URL(JConfig.INSTANCE.getSeleniumHub()),
                        new DesiredCapabilities(JConfig.INSTANCE.getBrowserName(), "", Platform.ANY)));
                try {
                    base.evaluate();
                } finally {
                    try {
                        browser.getDriver().quit();
                    } catch (WebDriverException ignored) {
                    }
                }
            }
        };
    }
}
