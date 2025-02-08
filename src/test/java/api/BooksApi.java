package api;

import models.AddBooksListModel;
import models.DeleteBookModel;
import models.LoginResponseModel;
import specs.ApiSpec;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class BooksApi {
    public void deleteAllBooks(LoginResponseModel loginResponse) {
        given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParam("UserId", loginResponse.getUserId())
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .spec(ApiSpec.deleteResponseSpec);
    }

    public void addBook(LoginResponseModel loginResponse, AddBooksListModel booksList) {

        given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(booksList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(ApiSpec.createdResponseSpec);
    }
    public void deleteBook(LoginResponseModel loginResponse, String isbn) {
        DeleteBookModel deleteBook = new DeleteBookModel(loginResponse.getUserId(), isbn);

        given(ApiSpec.baseRequestSpec)
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(deleteBook)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .log().all()
                .spec(ApiSpec.deleteResponseSpec);
    }
}
