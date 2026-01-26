package tests;

import api.client.CourierClient;
import api.models.Courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Проверка успешного создания курьера")
    public void courierCanBeCreated() {
        courier = Courier.getRandom();
        Response response = courierClient.create(courier);
        response.then()
                .statusCode(201)
                .body("ok", is(true));
        getCourierIdForCleanup();
    }

    @Test
    @DisplayName("Проверка создания дубликата курьера")
    public void duplicateCourierCreationFails() {
        courier = Courier.getRandom();
        courierClient.create(courier);
        Response response = courierClient.create(courier);
        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        getCourierIdForCleanup();
    }

    @Test
    @DisplayName("Проверка создания курьера без логина")
    public void courierCreationWithoutLoginFails() {
        courier = new Courier(null, "password123","Иван");
        Response response = courierClient.create(courier);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Получить ID курьера для последующей очистки")
    private void getCourierIdForCleanup() {
        Response loginResponse = courierClient.login(Courier.getCredentialsFrom(courier));
        courierId = loginResponse.body().path("id");
    }
}
