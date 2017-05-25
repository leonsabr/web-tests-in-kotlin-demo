package com.leonsabr.demo.utils;

import com.leonsabr.demo.pages.JPage;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class JWaiters {

    private JWaiters() {
    }

    public static <T extends JPage> T waitForPage(final T page, final long timeoutInSeconds) {
        final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeoutInSeconds);

        while (System.currentTimeMillis() < deadline) {
            if (page.isLoaded()) return page;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        throw new AssertionError("Failed to wait for " + page + " to load!");
    }

    public static <T extends JPage> T waitForPage(T page) {
        return waitForPage(page, 15);
    }

    public static void waitForCondition(final String message, final HtmlElement element,
                                      final Function<HtmlElement, Boolean> condition, final long timeoutInSeconds) {
        final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeoutInSeconds);

        while (System.currentTimeMillis() < deadline) {
            if (condition.apply(element)) return;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        throw new AssertionError(message);
    }

    public static void waitForElementExists(final HtmlElement element) {
        waitForCondition("Can't find " + element + "!", element, exists(), 15);
    }

    public static Function<HtmlElement, Boolean> exists() {
        return HtmlElement::exists;
    }

    public static Function<HtmlElement, Boolean> notExists() {
        return element -> !element.exists();
    }


    public static void waitForCondition(final String message, final TypifiedElement element,
                                        final Function<TypifiedElement, Boolean> condition, final long timeoutInSeconds) {
        final long deadline = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeoutInSeconds);

        while (System.currentTimeMillis() < deadline) {
            if (condition.apply(element)) return;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }

        throw new AssertionError(message);
    }

    public static void waitForElementExists(final TypifiedElement element) {
        waitForCondition("Can't find " + element + "!", element, typifiedExists(), 15);
    }

    public static Function<TypifiedElement, Boolean> typifiedExists() {
        return TypifiedElement::exists;
    }

    public static Function<TypifiedElement, Boolean> typifiedNotExists() {
        return element -> !element.exists();
    }
}
