package com.leonsabr.demo.allure;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import ru.yandex.qatools.allure.junit.AllureRunListener;

public class JAllureTestRunner extends BlockJUnit4ClassRunner {

    public JAllureTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        notifier.addListener(new AllureRunListener());
        notifier.fireTestRunStarted(Description.createSuiteDescription("this is not important"));
        super.run(notifier);
        notifier.fireTestRunFinished(new Result());
    }
}
