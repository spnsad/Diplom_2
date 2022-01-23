import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SuccessCreateOrderByAuthAndNonAuthUserTest {

    List<String> ingredients = new ArrayList<>();
    public String orderIngredient;
    public String accessToken;
    public static UserClient userClient;
    public User user;

    @Step
    @Before
    public void setUp() {
        user = User.generateRandomUser();
        userClient = new UserClient();
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
    @DisplayName("Создание заказа неавторизованным пользователем с указанием ингредиентов")
    public void createOrderByNonAuthUserTest() {
        IngredientsData createOrderBody = new IngredientsData(orderIngredient);
        ValidatableResponse response = new OrdersClient().createOrderWOAuth(createOrderBody);
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertNotNull("В ответе не вернулся номер заказа", response.extract().path("order.number"));
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем с указанием ингредиентов")
    public void createOrderByAuthUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        IngredientsData createOrderBody = new IngredientsData(orderIngredient);
        ValidatableResponse response = new OrdersClient().createOrderWithAuth(createOrderBody, accessToken);
        Assert.assertEquals(200, response.extract().statusCode());
        Assert.assertNotNull("В ответе не вернулся номер заказа", response.extract().path("order.number"));
    }
}
