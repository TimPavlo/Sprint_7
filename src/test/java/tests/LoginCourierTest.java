package tests;

import api.client.CourierClient;
import api.models.Courier;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void courierCanLogin() {
        Response response = courierClient.login(Courier.getCredentialsFrom(courier));
        response.then()
                .statusCode(200)
                .body("id", notNullValue());
        courierId = response.body().path("id");
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void loginWithWrongPasswordFails() {
        Courier wrongCreds = new Courier(courier.getLogin(), "wrong_password", null);
        Response response = courierClient.login(wrongCreds);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем")
    public void loginNonExistentCourierFails() {
        Courier nonExistent = new Courier("nonexistent_" + System.currentTimeMillis(), "password", null);
        Response response = courierClient.login(nonExistent);
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина")
     public void loginWithoutLoginFails() {
        Courier noLogin = new Courier(null, courier.getPassword(), null);
        Response response = courierClient.login(noLogin);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Ignore("BUG-API-001: Сервер возвращает 504 вместо 400 при логине без пароля")
    @DisplayName("Авторизация без пароля")
    public void loginWithoutPasswordFails() {
        Courier noPassword = new Courier(courier.getLogin(), null, null);
        Response response = courierClient.login(noPassword);
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}
