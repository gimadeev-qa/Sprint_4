package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

/**
 * Фабрика драйверов. Наследуется от ExternalResource,
 * поэтому её удобно использовать как @Rule в тестах:
 * браузер сам откроется до теста и закроется после.
 */
public class DriverFactory extends ExternalResource {

    private WebDriver driver;

    // Определяем, какой браузер запускать
    public void init() {
        String browser = System.getProperty("browser", "chrome");
        if ("firefox".equalsIgnoreCase(browser)) {
            initFirefox();
        } else {
            initChrome();
        }
    }

    // Запускаем Chrome
    private void initChrome() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    // Запускаем Firefox
    private void initFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public WebDriver getDriver() {
        return driver;
    }

    // Вызывается до теста (JUnit @Rule)
    @Override
    protected void before() {
        init();
    }

    // Вызывается после теста (JUnit @Rule)
    @Override
    protected void after() {
        if (driver != null) {
            driver.quit();
        }
    }
}