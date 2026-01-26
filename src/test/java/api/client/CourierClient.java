package api.client;

import api.models.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Step("Создать нового курьера")
    public Response create(Courier courier) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
        .when()
                .post("/api/v1/courier");
        return response;
    }

    @Step("Залогинить курьера")
    public Response login(Courier creds) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(creds)
        .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Удалить курьера по Id: {id}")
    public Response deleteCourier(int id) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
        .when()
                .delete("/api/v1/courier/" + id);
        return response;
    }
}
