package api.client;

import api.models.Order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Step("Создать новый заказ")
    public Response create(Order order) {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
        .when()
                .post("/api/v1/orders");
        return response;
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        Response response = given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
        .when()
                .get("/api/v1/orders");
        return response;
    }
}
