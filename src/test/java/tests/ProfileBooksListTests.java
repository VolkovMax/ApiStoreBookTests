package tests;


import models.AddBooksListModel;
import models.IsbnModel;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;


import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static tests.TestData.credentials;


public class ProfileBooksListTests extends TestBase {
    @Test
    void addBookToProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn("9781449325862");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        AddBooksListModel booksList = new AddBooksListModel();
        booksList.setUserId(loginResponse.getUserId());
        booksList.setCollectionOfIsbns(isbnList);

        booksApi.addBook(loginResponse, booksList);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));

        open("/profile");
        $("[id='see-book-Git Pocket Guide']").shouldBe(visible);
    }

    @Test
    @DisplayName("Удаление книги из профиля")

    void deleteBookFromProfileTest() {
        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        booksApi.deleteAllBooks(loginResponse);

        // Добавляем книгу
        IsbnModel isbnModel = new IsbnModel();
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        AddBooksListModel booksList = new AddBooksListModel(loginResponse.getUserId(), isbnList);
        booksApi.addBook(loginResponse, booksList);

        // Проверяем, что книга появилась
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));

        open("/profile");
        $("[id='see-book-Git Pocket Guide']").shouldBe(visible);

        // Удаляем книгу
        booksApi.deleteBook(loginResponse, "9781449325862");

        // Проверяем, что книга исчезла
        open("/profile");
        $("[id='see-book-Git Pocket Guide']").shouldNotBe(visible);
    }
}
