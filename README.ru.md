##Как запустить тесты
*Read this in [English](README.md).*

Работоспособность приведенных ниже шагов тестировалась на
* macOS 10.12.3 и Ubuntu 16.04
* Java 1.8.0_111
* Docker 1.13.0

Пользователям Windows рекомендуется использовать Docker for Windows и скрипт `gradlew.bat` (вместо просто `gradlew`).

### 1. Установите Docker
Docker требуется для разворачивания тестируемой системы (сервер TeamCity).
[Скачайте](https://www.docker.com/products/overview#install_the_platform) Docker, подходящий вашей операционной системе, и следуйте инструкциям.

### 2. Запустите Selenium сервер
Вы можете использовать доступный вам Selenium Hub.
В любом случае, можете загрузить [chromedriver](https://chromedriver.storage.googleapis.com/index.html), [geckodriver](https://github.com/mozilla/geckodriver/releases) и [selenium-server-standalone](http://selenium-release.storage.googleapis.com/index.html) и выполнить
```
java -Dwebdriver.chrome.driver=/path/to/chromedriver-2.27 -Dwebdriver.gecko.driver=/path/to/geckodriver-0.14.0 -jar -Xmx256m /path/to/selenium-server-standalone-3.1.0.jar
```
Запустится локальный Selenium сервер. Запрос браузерных сессий следует отправлять на URL `http://127.0.0.1:4444/wd/hub`.

### 3. (Дополнительно) Установите Allure commandline
Если хотите построить Allure отчет по тестам, вам потребуется установить Allure commandline. Следуйте [инструкциям](http://wiki.qatools.ru/display/AL/Allure+Commandline) и проверьте корректность установки командой
```
$ allure version
1.4.23
```

### 4. Клонируйте репозиторий, задайте параметры
По умолчанию тесты запускаются в `firefox`, получая браузерные сессии на `http://127.0.0.1:4444/wd/hub`.
Сервер TeamCity запускается на `http://hostname:7777`, где `hostname` - это имя вашей локальной машины.
Если вы хотите изменить параметры по умолчанию, в папке с проектом создайте файл `local.gradle` со следующим содержимым:
```
ext {
    seleniumHub = "http://some-hub-host.company.com:5555/wd/hub"
    browserName = "chrome"
    teamcityServerPort = "9999"
 }
```

### 5. Запустите тесты
Уберите задачу `generateAllureReport`, если у вас не установлен Allure commandline.
```
./gradlew clean test generateAllureReport
```
Отчет `build/reports/allure/index.html`.
