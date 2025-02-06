package api;



import models.AddBooksListModel;
import models.LoginResponseModel;

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
                .statusCode(204);
    }

    public void addBook(LoginResponseModel loginResponse, AddBooksListModel booksList) {
        given()
                .contentType(JSON)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(booksList)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .statusCode(201);
    }
}
