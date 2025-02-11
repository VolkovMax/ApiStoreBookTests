package tests;

import api.AuthorizationApi;
import api.BooksApi;
import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    AuthorizationApi authorizationApi = new AuthorizationApi();
    BooksApi booksApi = new BooksApi();

    @BeforeAll
    static void setup() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.browser = System.getProperty("browser", "firefox");
        Configuration.browserSize = System.getProperty("browserSize", "1280x1024");
        Configuration.browserVersion = System.getProperty("browserVersion", "123.0");
        Configuration.timeout = 10000;
        Configuration.remote = "https://"
                + System.getProperty("login")
                + ":"
                + System.getProperty("pass")
                + "@"
                + System.getProperty("host")
                + "/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true ));
    }


    @AfterAll
    static void shutDown() {
        closeWebDriver();
    }
}
