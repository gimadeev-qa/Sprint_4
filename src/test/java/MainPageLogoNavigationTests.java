import model.MainPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import utils.DriverFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Факультативные тесты на логотипы в шапке сайта:
 *  1) клик по логотипу Самоката ведёт на главную страницу Самоката;
 *  2) клик по логотипу Яндекса открывает главную страницу Яндекса
 *     в новом окне (вкладке).
 */
public class MainPageLogoNavigationTests {

    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String SCOOTER_BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    private static final String YANDEX_URL_PART = "https://yandex.ru";

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private MainPage mainPage;

    @Before
    public void before() {
        mainPage = new MainPage(driverFactory.getDriver());
    }

    /**
     * Тест 1: клик по логотипу Самоката должен вернуть на главную Самоката.
     */
    @Test
    public void testScooterLogoOpensMainPage() {
        mainPage.openMainPage(MAIN_PAGE_URL);
        mainPage.closeCookie();
        mainPage.clickScooterLogo();

        // ИСПРАВЛЕНО: WebDriverWait заменён на mainPage.waitForUrlContains()
        mainPage.waitForUrlContains(SCOOTER_BASE_URL);

        // ИСПРАВЛЕНО: driver.getCurrentUrl() заменён на mainPage.getCurrentUrl()
        assertTrue("Клик по логотипу Самоката не привёл на главную страницу",
                mainPage.getCurrentUrl().startsWith(SCOOTER_BASE_URL));
    }

    /**
     * Тест 2: клик по логотипу Яндекса открывает yandex.ru в новой вкладке.
     */
    @Test
    public void testYandexLogoOpensYandexInNewTab() {
        mainPage.openMainPage(MAIN_PAGE_URL);
        mainPage.closeCookie();

        // Запоминаем исходное окно
        String originalWindow = mainPage.getCurrentWindowHandle();

        //  Кликаем по логотипу Яндекса
        mainPage.clickYandexLogo();

        //  Ждём появления второй вкладки
        mainPage.waitForNewWindow();

        // Переключаемся на новую вкладку
        mainPage.switchToWindowOtherThan(originalWindow);

        // Берем URLновой вкладки и сравниваем с Яндексом
        String newTabUrl = mainPage.getCurrentUrl();
        assertTrue("Логотип Яндекса не открыл yandex.ru. Текущий URL: " + newTabUrl,
                newTabUrl.contains(YANDEX_URL_PART));

        // Закрываем вкладку Яндекса и возвращаемся на исходную
        mainPage.closeCurrentWindowAndSwitchTo(originalWindow);

        assertEquals("После возврата должны быть на исходной странице",
                MAIN_PAGE_URL, mainPage.getCurrentUrl());
    }

}