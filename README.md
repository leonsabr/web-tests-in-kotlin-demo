##How to run tests
*[Русский](README.ru.md) перевод.*

The following steps were tested using
* macOS 10.12.3 and Ubuntu 16.04
* Java 1.8.0_111
* Docker 1.13.0

Windows-users are recommended to use Docker for Windows and `gradlew.bat` (instead of `gradlew`).

### 1. Setup Docker
Docker is required to run containers with product under test (TeamCity server).
Visit [Docker site](https://www.docker.com/products/overview#install_the_platform), download Docker for your OS and follow instructions.

### 2. Start Selenium server
You can use any available Selenium Hub installation.
Anyway, you can download [chromedriver](https://chromedriver.storage.googleapis.com/index.html), [geckodriver](https://github.com/mozilla/geckodriver/releases) and [selenium-server-standalone](http://selenium-release.storage.googleapis.com/index.html) and run
```
java -Dwebdriver.chrome.driver=/path/to/chromedriver-2.27 -Dwebdriver.gecko.driver=/path/to/geckodriver-0.14.0 -jar -Xmx256m /path/to/selenium-server-standalone-3.1.0.jar
```
You local Selenium server is up and running. URL to connect is `http://127.0.0.1:4444/wd/hub`.

### 3. (Optional) Install Allure commandline
If you want to build a report you have to install Allure commandline tool. Follow [instructions](http://wiki.qatools.ru/display/AL/Allure+Commandline) and verify correct installation with the command
```
$ allure version
1.4.23
```

### 4. Clone repository, set parameters
By default, tests are run in `firefox` and obtain new browser sessions on `http://127.0.0.1:4444/wd/hub`.
TeamCity server starts on `http://hostname:7777` where `hostname` is your local machine hostname.
If you want to change the default parameters, create `local.gradle` file in the project's directory with the following content:
```
ext {
    seleniumHub = "http://some-hub-host.company.com:5555/wd/hub"
    browserName = "chrome"
    teamcityServerPort = "9999"
 }
```

### 5. Run tests
Skip `generateAllureReport` task if you do not have Allure commandline.
```
./gradlew clean test generateAllureReport
```
The report is located in `build/reports/allure/index.html`.
