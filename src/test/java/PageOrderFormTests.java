import model.OrderForm;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import utils.DriverFactory;

import static org.junit.Assert.assertTrue;

/**
 * Позитивный сценарий заказа самоката с двумя наборами данных.
 */
@RunWith(Parameterized.class)
public class PageOrderFormTests {

    private static final String ORDER_PAGE_URL =
            "https://qa-scooter.praktikum-services.ru/order";

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private final String name;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String colorScooter;
    private final String comment;

    private OrderForm orderForm;

    public PageOrderFormTests(String name, String lastName, String address,
                              String metroStation, String phone,
                              String date, String rentalPeriod,
                              String colorScooter, String comment) {
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.colorScooter = colorScooter;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{0} {1}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Мария", "Иванова", "город Москва", "Сокольники",
                        "+79123456789", ".20.09.2026", "сутки", "black", "Мой личный комментарий"},
                {"Иван", "Петров", "улица Ленина", "Лубянка",
                        "+79876543210", "15.09.2026", "трое суток", "grey", "Без комментариев"},
        };
    }

    @Before
    public void before() {
        orderForm = new OrderForm(driverFactory.getDriver());
    }

    @Test
    public void testMakingOrder() {
        // ИСПРАВЛЕНО: driver.get() заменён на orderForm.openOrderPage()
        orderForm.openOrderPage(ORDER_PAGE_URL);
        orderForm.closeCookie();

        // Первая часть формы: "Для кого самокат"
        orderForm.testFieldName(name);
        orderForm.testFieldLastName(lastName);
        orderForm.testFieldAddress(address);
        orderForm.testFieldMetroStation(metroStation);
        orderForm.testFieldPhone(phone);
        orderForm.pushButtonNext();

        // Вторая часть формы: "Про аренду"
        assertTrue("Страница 'Про аренду' не открылась",
                orderForm.testPageAboutRentIsDisplayed());

        orderForm.testFieldDeliveryDate(date);
        orderForm.testFieldRentalPeriod(rentalPeriod);
        orderForm.testFieldColorScooter(colorScooter);
        orderForm.testComment(comment);
        orderForm.testButtonOrder();
        orderForm.testButtonYesOrder();

        // Проверяем сообщение об успешном оформлении
        assertTrue("Нет сообщения об успешном заказе",
                orderForm.testMessageOrderOk().contains("Заказ оформлен"));
    }
}