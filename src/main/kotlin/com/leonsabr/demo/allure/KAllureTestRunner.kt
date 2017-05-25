package com.leonsabr.demo.allure

import org.junit.runner.Description
import org.junit.runner.Result
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import ru.yandex.qatools.allure.junit.AllureRunListener

class KAllureTestRunner(clazz: Class<*>) : BlockJUnit4ClassRunner(clazz) {
    override fun run(notifier: RunNotifier) {
        notifier.addListener(AllureRunListener())
        notifier.fireTestRunStarted(Description.createSuiteDescription("this is not important"))
        super.run(notifier)
        notifier.fireTestRunFinished(Result())
    }
}
