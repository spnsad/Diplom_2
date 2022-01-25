import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderWithWrongIngredientsDataTest {
    public OrdersClient ordersClient;
    public UserClient userClient;
    public User user;
    public String accessToken;

    @Step
    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.generateRandomUser();
        ordersClient = new OrdersClient();
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
    @DisplayName("Создание заказа авторизованным пользователем с некорректными ингредиентами")
    public void createOrderWithWrongIngredientHashByAuthUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        IngredientsData createOrderBody = new IngredientsData("invalid");
        ValidatableResponse response = ordersClient.createOrderWithAuth(createOrderBody, accessToken);
        Assert.assertEquals(500, response.extract().statusCode());
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем с некорректными ингредиентами")
    public void createOrderWithWrongIngredientHashByNonAuthUserTest() {
        IngredientsData createOrderBody = new IngredientsData("invalid");
        ValidatableResponse response = ordersClient.createOrderWOAuth(createOrderBody);
        Assert.assertEquals(500, response.extract().statusCode());
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем без указания ингредиентов")
    public void createOrderWOIngredientsByAuthUserTest() {
        accessToken = userClient.createUser(user).extract().path("accessToken");
        IngredientsData createOrderBody = new IngredientsData(null);
        ValidatableResponse response = ordersClient.createOrderWithAuth(createOrderBody, accessToken);

        Assert.assertEquals(400, response.extract().statusCode());
        Assert.assertEquals("Ingredient ids must be provided", response.extract().path("message").toString());
    }
}
