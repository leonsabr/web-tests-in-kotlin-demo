package com.leonsabr.demo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public abstract class JPage {

    protected final WebDriver driver;

    public JPage(final WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new HtmlElementDecorator(new HtmlElementLocatorFactory(driver)), this);
    }

    public abstract String getUrl();

    public abstract boolean isLoaded();

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
