import model.MainPage;
import model.OrderForm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.DriverFactory;

import static org.junit.Assert.assertTrue;

/**
 * Проверяем, что обе кнопки "Заказать" (в шапке и внизу)
 * открывают форму заказа.
 */
@RunWith(Parameterized.class)
public class MainPageOrderButtonTests {

    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private final String selectButton;

    private MainPage mainPage;
    private OrderForm orderForm;

    public MainPageOrderButtonTests(String selectButton) {
        this.selectButton = selectButton;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Object[][] dataTestButtonOrder() {
        return new Object[][]{
                {"Проверить верхнюю кнопку"},
                {"Проверить нижнюю кнопку"},
        };
    }

    @Before
    public void before() {
        mainPage = new MainPage(driverFactory.getDriver());
        orderForm = new OrderForm(driverFactory.getDriver());
    }

    @Test
    public void testButtonOrder() {
        // ИСПРАВЛЕНО: driver.get() заменён на mainPage.openMainPage()
        mainPage.openMainPage(MAIN_PAGE_URL);
        mainPage.closeCookie();

        if ("Проверить верхнюю кнопку".equals(selectButton)) {
            mainPage.clickButtonOrderInTheHeader();
        } else if ("Проверить нижнюю кнопку".equals(selectButton)) {
            mainPage.clickButtonOrderAtTheBottom();
        } else {
            throw new IllegalArgumentException(
                    "Ожидается 'Проверить верхнюю кнопку' или 'Проверить нижнюю кнопку'");
        }

        assertTrue("Форма заказа не отображается", orderForm.pageIsDisplayed());
    }
}