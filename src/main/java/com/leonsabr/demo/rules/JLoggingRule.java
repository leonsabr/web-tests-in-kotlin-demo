package com.leonsabr.demo.rules;

import com.leonsabr.demo.allure.JAttachments;
import com.leonsabr.demo.utils.JBrowser;
import org.junit.AssumptionViolatedException;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.leonsabr.demo.allure.JAttachments.attachScreenshot;
import static com.leonsabr.demo.allure.JAttachments.attachText;

public class JLoggingRule implements TestRule {

    private final JBrowser browser;
    private Boolean needLogs;

    public JLoggingRule(final JBrowser browser, final boolean needLogs) {
        this.browser = browser;
        this.needLogs = needLogs;
    }

    public JLoggingRule(final JBrowser browser) {
        this(browser, false);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable e) {
                    needLogs = !(e instanceof AssumptionViolatedException);
                    throw e;
                } finally {
                    if (needLogs) {
                        JAttachments.attachScreenshot("Screenshot of the failure", browser.getDriver());
                        JAttachments.attachText("The last URL in browser", browser.getDriver().getCurrentUrl());
                    }
                }
            }
        };
    }
}
