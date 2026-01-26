package tests;

import api.client.OrderClient;
import api.models.Order;
import api.models.OrderGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final OrderClient orderClient = new OrderClient();
    private final Order testOrder;
    private final String description;

    public CreateOrderTest (Order testOrder, String description) {
        this.testOrder = testOrder;
        this.description = description;
    }
@Parameterized.Parameters(name = "Тест: {1}")
public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {OrderGenerator.getOrderBlack(), "Заказ с цветом BLACK"},
                {OrderGenerator.getOrderGrey(), "Заказ с цветом GREY"},
                {OrderGenerator.getOrderBothColors(), "Заказ с обоими цветами"},
                {OrderGenerator.getOrderWithoutColor(), "Заказ без указания цвета"}
        });
        }

@Test
@DisplayName("Создание заказа с разными вариантами цветов")
public void orderCanBeCreatedWithDifferentColors() {
        System.out.println("Выполняется: " + description);
        Response response = orderClient.create(testOrder);
        response.then()
                .statusCode(201)
                .body("track", notNullValue());
}
}
