package api;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import specs.ApiSpec;
import models.CredentialsModel;
import models.LoginResponseModel;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthorizationApi {

    public LoginResponseModel login(CredentialsModel credentials){
        Response response = given()
                .body(credentials)
                .contentType(JSON)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().all() // Логируем весь ответ для отладки
                .statusCode(200) // Проверяем, что статус-код 200
                .spec(ApiSpec.successResponseSpec)
                .extract().response();

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.asString(), LoginResponseModel.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse response: " + e.getMessage(), e);
        }
    }
}
