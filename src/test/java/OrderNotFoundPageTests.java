import model.MainPage;
import model.StatusPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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

    private MainPage mainPage;
    private StatusPage statusPage;

    @Before
    public void before() {
        mainPage = new MainPage(driverFactory.getDriver());
        statusPage = new StatusPage(driverFactory.getDriver());
    }

    @Test
    public void testWrongOrderNumber() {
        // ИСПРАВЛЕНО: driver.get() заменён на mainPage.openMainPage()
        mainPage.openMainPage(MAIN_PAGE_URL);
        mainPage.closeCookie();
        mainPage.clickOrderStatus();
        mainPage.enterOrderNumber("99999999");
        mainPage.clickGo();

        assertTrue("Сообщение 'Такого заказа нет' не отображается",
                statusPage.isNotFoundMessageDisplayed());
    }
}