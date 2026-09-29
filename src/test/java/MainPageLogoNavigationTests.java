import model.MainPage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverFactory;
import java.time.Duration;
import java.util.Set;
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

    private static final String YANDEX_URL_PART = "https:/yandex.ru";

    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private WebDriver driver;

    @Before
    public void before() {
        driver = driverFactory.getDriver();
    }

    /**
     * Тест 1: клик по логотипу Самоката должен вернуть на главную Самоката.
     */
    @Test
    public void testScooterLogoOpensMainPage() {
        driver.get(MAIN_PAGE_URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookie();
        mainPage.clickScooterLogo();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(SCOOTER_BASE_URL));

        // Проверяем, что мы всё ещё на главной Самоката
        assertTrue("Клик по логотипу Самоката не привёл на главную страницу",
                driver.getCurrentUrl().startsWith(SCOOTER_BASE_URL));
    }

    /**
     * Тест 2: клик по логотипу Яндекса открывает yandex.ru в новой вкладке.
     */
    @Test
    public void testYandexLogoOpensYandexInNewTab() {
        driver.get(MAIN_PAGE_URL);

        MainPage mainPage = new MainPage(driver);
        mainPage.closeCookie();

        // Запоминаем текущую вкладку
        String originalWindow = driver.getWindowHandle();

        // Кликаем по логотипу Яндекса
        mainPage.clickYandexLogo();

        // Ждём появления второй вкладки
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));

        // Переключаемся на новую вкладку
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        // Ждём загрузки URL новой вкладки
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(YANDEX_URL_PART));

        String newTabUrl = driver.getCurrentUrl();
        assertTrue("Логотип Яндекса не открыл yandex.ru. Текущий URL: " + newTabUrl,
                newTabUrl.contains(YANDEX_URL_PART));

        // Закрываем вкладку Яндекса и возвращаемся на исходную
        driver.close();
        driver.switchTo().window(originalWindow);

        assertEquals("После возврата должны быть на исходной странице",
                MAIN_PAGE_URL, driver.getCurrentUrl());
    }
}