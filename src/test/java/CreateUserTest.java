import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    private User user;
    private static UserClient userClient;
    public String accessToken;

    @Step
    @Before
    public void setUp() {
        user = User.generateRandomUser();
        userClient = new UserClient();
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
    @DisplayName("Регистрация пользователя с корректными данными")
    public void checkCreateUserWithValidData() {

        ValidatableResponse response = userClient.createUser(user);
        int statusCode = response.extract().statusCode();
        accessToken = response.extract().path("accessToken");

        assertThat("Код ответа некорректный", statusCode, equalTo(200));
    }

    @Test
    @DisplayName("Повторная регистрация пользователя")
    public void checkCreateRepeatedUser() throws NullPointerException{
        userClient.createUser(user);
        ValidatableResponse response = userClient.createUser(user);

        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");
        accessToken = response.extract().path("accessToken");

        assertThat("Повторная регистрация не должна была пройти", errorMessage, equalTo("User already exists"));
        assertThat("Код ответа некорректный", statusCode, equalTo(403));
    }
}
