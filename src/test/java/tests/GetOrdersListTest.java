package tests;

import api.client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrdersListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersListReturnsOrders() {
        Response response = orderClient.getOrdersList();
        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
