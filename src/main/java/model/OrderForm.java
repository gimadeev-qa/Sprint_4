package model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object формы заказа самоката (обе страницы:
 * "Для кого самокат" и "Про аренду").
 */
public class OrderForm {

    private WebDriver driver;

    // Кнопка "да все привыкли"
    private By closeCookie = By.xpath("//button[text()='да все привыкли']");

    // Заголовок страницы "Для кого самокат"
    private By titlePage = By.xpath("//div[text()='Для кого самокат']");

    // Поля первой страницы
    private By fieldName = By.xpath("//input[@placeholder='* Имя']");
    private By fieldLastName = By.xpath("//input[@placeholder='* Фамилия']");
    private By fieldAddress = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By fieldMetroStation = By.xpath("//input[@placeholder='* Станция метро']");
    private By fieldPhone = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private By buttonNext = By.xpath("//button[contains(@class,'Button_Button__') and text()='Далее']");

    // Заголовок страницы "Про аренду"
    private By titlePageAboutRent = By.xpath("//div[text()='Про аренду']");

    // Поля второй страницы
    private By fieldDeliveryDate = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By buttonRentalPeriod = By.xpath("//span[contains(@class,'Dropdown-arrow')]");
    private By fieldComment = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопки "Заказать" и "Да"
    private By buttonOrder = By.xpath("//button[contains(@class,'Button_Middle__') and text()='Заказать']");
    private By buttonYesOrder = By.xpath("//button[contains(@class,'Button_Middle__') and text()='Да']");

    // Модальное окно с сообщением об успешном заказе
    private By messageOrderOk = By.xpath("//div[contains(@class,'Order_ModalHeader__')]");

    public OrderForm(WebDriver driver) {
        this.driver = driver;
    }

    // Принять куки если появились. так как окно закрывает элементы
    public void closeCookie() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(closeCookie))
                .click();
    }

    // Проверяем, что открыта страница "Для кого самокат"
    public boolean pageIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(titlePage));
        return driver.findElement(titlePage).isDisplayed();
    }

    // Заполняем поле Имя
    public void testFieldName(String name) {
        driver.findElement(fieldName).sendKeys(name);
    }

    // Заполняем поле Фамилия
    public void testFieldLastName(String lastName) {
        driver.findElement(fieldLastName).sendKeys(lastName);
    }

    // Заполняем поле Адрес
    public void testFieldAddress(String address) {
        driver.findElement(fieldAddress).sendKeys(address);
    }

    // Заполняем станцию метро и выбираем из подсказки
    public void testFieldMetroStation(String metroStation) {
        driver.findElement(fieldMetroStation).sendKeys(metroStation);

        By stationOption = By.xpath(
                "//div[contains(@class,'Order_Text__') and text()='" + metroStation + "']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(stationOption))
                .click();
    }
    // Заполняем номер телефона
    public void testFieldPhone(String phone) {
        driver.findElement(fieldPhone).sendKeys(phone);
    }

    // Нажать "Далее"
    public void pushButtonNext() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(buttonNext))
                .click();
    }

    // Проверяем, что открыта страница "Про аренду"
    public boolean testPageAboutRentIsDisplayed() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(titlePageAboutRent));
        return driver.findElement(titlePageAboutRent).isDisplayed();
    }

    // Ввод даты + Enter, чтобы закрылся календарь
    public void testFieldDeliveryDate(String date) {
        driver.findElement(fieldDeliveryDate).sendKeys(date);
        driver.findElement(fieldDeliveryDate).sendKeys(Keys.ENTER);
    }

    // Выбор срока аренды
    public void testFieldRentalPeriod(String rentalPeriod) {
        driver.findElement(buttonRentalPeriod).click();

        By rentalOption = By.xpath("//div[text()='" + rentalPeriod + "']");
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(rentalOption))
                .click();
    }

    // Выбор цвета самоката (id = "black" или "grey")
    public void testFieldColorScooter(String colorScooter) {
        driver.findElement(By.id(colorScooter)).click();
    }

    // Заполняем поле Комментарий
    public void testComment(String comment) {
        driver.findElement(fieldComment).sendKeys(comment);
    }

    // Нажимаем на кнопку Заказать
    public void testButtonOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(buttonOrder))
                .click();
    }

    // Нажимаем на кнопку Да для подтверждения заказа
    public void testButtonYesOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(buttonYesOrder))
                .click();
    }

    // Возвращаем текст модального окна с успешным заказом
    public String testMessageOrderOk() {
        new WebDriverWait(driver, Duration.ofSeconds(7))
                .until(ExpectedConditions.visibilityOfElementLocated(messageOrderOk));
        return driver.findElement(messageOrderOk).getText();
    }
}