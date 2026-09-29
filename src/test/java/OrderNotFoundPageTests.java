import model.MainPage;
import model.StatusPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

import static org.junit.Assert.assertTrue;

/**
 * Проверяем, что при вводе несуществующего номера заказа
 * на странице статуса появляется сообщение "Такого заказа нет".
 */
public class OrderNotFoundPageTests {

    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private WebDriver driver;

    @Before
    public void before() {
        driver = driverFactory.getDriver();
    }

    @Test
    public void testWrongOrderNumber() {
        driver.get(MAIN_PAGE_URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookie();
        mainPage.clickOrderStatus();
        mainPage.enterOrderNumber("99999999");
        mainPage.clickGo();

        StatusPage statusPage = new StatusPage(driver);
        assertTrue("Сообщение 'Такого заказа нет' не отображается",
                statusPage.isNotFoundMessageDisplayed());
    }
}