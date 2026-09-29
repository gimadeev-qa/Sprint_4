package model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

/**
 * Page Object главной страницы Яндекс Самоката.
 */
public class MainPage {

    private WebDriver driver;

    // Логотип Самоката (в шапке)
    private By scooterLogo = By.cssSelector("a[class*='Header_LogoScooter__']");

    // Логотип Яндекса (в шапке)
    private By yandexLogo = By.cssSelector("a[class*='Header_LogoYandex__']");

    // Кнопка "Заказать" в шапке
    private By orderButtonTop = By.xpath("(//button[text()='Заказать'])[1]");

    // Кнопка "Заказать" внизу страницы
    private By orderButtonBottom = By.xpath("//button[contains(@class,'Button_UltraBig') and text()='Заказать']");

    // Кнопка "Статус заказа" в шапке
    private By orderStatusButton = By.xpath("//button[text()='Статус заказа']");

    // Поле ввода номера заказа
    private By orderNumberInput = By.xpath("//input[@placeholder='Введите номер заказа']");

    // Кнопка "Go!" на странице статуса
    private By goButton = By.xpath("//button[text()='Go!']");

    // Кнопка принять куки
    private By closeCookieButton = By.xpath("//button[text()='да все привыкли']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Принимаем куки
    public void closeCookie() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(closeCookieButton))
                .click();
    }

    // Нажимаем верхнюю кнопку "Заказать"
    public void clickButtonOrderInTheHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(orderButtonTop))
                .click();
    }

    // Нажимаем нижнюю кнопку "Заказать"
    public void clickButtonOrderAtTheBottom() {
        WebElement element = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(orderButtonBottom))
                .click();
    }

    // Кликаем по вопросу в разделе "Вопросы о важном"
    // ИСПРАВЛЕНО: убран Thread.sleep(300). Вместо него используем явное ожидание
    // видимости элемента и клик через Actions (надёжно для Firefox).
    public void questionClick(String question) {
        By questionLocator = By.xpath("//div[contains(text(), '" + question + "')]");

        // Явное ожидание: элемент присутствует в DOM и видим
        WebElement questionElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(questionLocator));

        // Прокручиваем элемент в центр экрана
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", questionElement);

        // Клик через Actions — надёжно и без JS-обмана
        new Actions(driver)
                .moveToElement(questionElement)
                .click()
                .perform();
    }

    // Проверяем, что ответ на вопрос отображается
    public boolean answerIsDisplayed(String answer) {
        By answerLocator = By.xpath("//p[text() = '" + answer + "']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
        return driver.findElement(answerLocator).isDisplayed();
    }

    // Открыть статус заказа
    public void clickOrderStatus() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(orderStatusButton))
                .click();
    }

    // Ввести номер заказа на странице статуса
    public void enterOrderNumber(String number) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(orderNumberInput))
                .sendKeys(number);
    }

    // Нажать кнопку Go!
    public void clickGo() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(goButton))
                .click();
    }

    // Клик по логотипу Самоката
    public void clickScooterLogo() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(scooterLogo))
                .click();
    }

    // Клик по логотипу Яндекса
    // ИСПРАВЛЕНО: добавлено явное ожидание перед кликом.
    public void clickYandexLogo() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(yandexLogo))
                .click();
    }

    // ИСПРАВЛЕНО: добавлен метод для открытия главной страницы,
    // чтобы убрать driver.get() из тестов.
    public void openMainPage(String url) {
        driver.get(url);
    }

    // ИСПРАВЛЕНО: добавлен метод ожидания, что URL содержит заданную подстроку.
    // Используется в тестах логотипов вместо WebDriverWait в тесте.
    public void waitForUrlContains(String urlPart) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(urlPart));
    }

    // Запоминаем handle текущего окна (до клика)
    public String getCurrentWindowHandle() {
        return driver.getWindowHandle();
    }

    // Ожидаем появление второго окна (вкладки)
    public void waitForNewWindow() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    // Переключаемся на окно, отличное от переданного
    public void switchToWindowOtherThan(String originalWindow) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                return;
            }
        }
        throw new IllegalStateException("Не найдено окно, отличное от " + originalWindow);
    }

    // ИСПРАВЛЕНО: добавлен метод для получения текущего URL,
    // чтобы убрать driver.getCurrentUrl() из тестов.
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ИСПРАВЛЕНО: добавлен метод для закрытия текущего окна и переключения
    // на указанное окно.
    public void closeCurrentWindowAndSwitchTo(String windowHandle) {
        driver.close();
        driver.switchTo().window(windowHandle);
    }
}