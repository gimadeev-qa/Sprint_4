package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object страницы статуса заказа
 * (используется для проверки несуществующего заказа).
 */
public class StatusPage {

    private WebDriver driver;

    // Сообщение "Такого заказа нет"
    private By notFoundMessage = By.cssSelector("img[alt='Not found']");

    public StatusPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод для проверки появляющегося окна с текстом "Такого заказа нет"
    public boolean isNotFoundMessageDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(notFoundMessage));
            return driver.findElement(notFoundMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}