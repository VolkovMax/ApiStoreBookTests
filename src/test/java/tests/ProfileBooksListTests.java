package tests;


import models.AddBooksListModel;
import models.IsbnModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import java.util.List;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static tests.TestData.credentials;

@Tag("simple")
public class ProfileBooksListTests extends TestBase {

    @Test
    @DisplayName("Добавление книги в профиль")
    void addBookToProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        step("Создаём объект книги", () -> {
            IsbnModel isbnModel = new IsbnModel(TestData.book.getIsbn());
            List<IsbnModel> isbnList = List.of(isbnModel);
            AddBooksListModel booksList = new AddBooksListModel(loginResponse.getUserId(), isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Добавляем куки авторизации в браузер", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });

        step("Открываем профиль и проверяем, что книга появилась", () -> {
            open("/profile");
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldBe(visible);
        });
    }

    @Test
    @DisplayName("Удаление книги из профиля")
    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        step("Добавляем книгу в профиль", () -> {
            IsbnModel isbnModel = new IsbnModel(TestData.book.getIsbn());
            List<IsbnModel> isbnList = List.of(isbnModel);
            AddBooksListModel booksList = new AddBooksListModel(loginResponse.getUserId(), isbnList);
            booksApi.addBook(loginResponse, booksList);
        });

        step("Добавляем куки авторизации в браузер", () -> {
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
            getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
            getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        });

        step("Открываем профиль и проверяем, что книга появилась", () -> {
            open("/profile");
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldBe(visible);
        });

        step("Удаляем книгу через API", () -> {
            booksApi.deleteBook(loginResponse, TestData.book.getIsbn());
        });

        step("Открываем профиль и проверяем, что книга исчезла", () -> {
            open("/profile");
            $("[id='see-book-" + TestData.book.getTitle() + "']").shouldNotBe(visible);
        });
    }
}