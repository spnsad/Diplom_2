import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetOrdersAuthNonAuthUserTest {
    public OrdersClient ordersClient;
    public UserClient userClient;
    public User user;
    public String accessToken;
    List<String> ingredients = new ArrayList<>();
    public String orderIngredient;

    @Step
    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.generateRandomUser();
        ordersClient = new OrdersClient();
        ingredients = new IngredientsClient().getAllIngredients().extract().path("data._id");
        orderIngredient = ingredients.get(0);
    }

    @Step
    @After
    public void tearDown() {
        if (accessToken == null) {
            return;
        }
        UserClient.delete(accessToken.substring(7));
    }

    @Test
    @DisplayName("Получение списка заказов авторизованного пользователя")
    public void getOrdersAuthUserTest() {
        //Создаем пользователя и получаем его токен
        accessToken = userClient.createUser(user).extract().path("accessToken");

        //Формируем тело заказа и делаем заказ
        IngredientsData createOrderBody = new IngredientsData(orderIngredient);
        ValidatableResponse createOrderResponse = new OrdersClient().createOrderWithAuth(createOrderBody, accessToken);
        Assert.assertEquals(200, createOrderResponse.extract().statusCode());

        //Получаем id заказа из ответа на запрос на его создание
        String orderIdFromCreate = createOrderResponse.extract().path("order._id");

        //Получаем список заказов созданного клиента
        ValidatableResponse getUserOrdersResponse = ordersClient.getOrdersAuth(accessToken);
        Assert.assertEquals(200, getUserOrdersResponse.extract().statusCode());
        Assert.assertTrue(getUserOrdersResponse.extract().path("success"));

        //Получаем номер заказа из ответа на запрос всех заказов клиента
        List<Map<String, String >> listOfUserOrders = getUserOrdersResponse.extract().path("orders");
        String orderIdFromOrdersRequest = listOfUserOrders.get(0).get("_id");

        Assert.assertEquals(orderIdFromCreate, orderIdFromOrdersRequest);
    }

    @Test
    @DisplayName("Получение списка заказов неавторизованного пользователя")
    public void getOrdersNotAuthUserTest() {
        ValidatableResponse response = ordersClient.getOrdersNonAuth();
        Assert.assertEquals(401, response.extract().statusCode());
        Assert.assertEquals("You should be authorised", response.extract().path("message"));
    }
}
