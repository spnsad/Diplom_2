import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginUserTest {
    private User user;
    private UserClient userClient;
    private String accessToken;

    @Step
    @Before
    public void setUp() {
        user = User.generateRandomUser();
        userClient = new UserClient();
        userClient.createUser(user);
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
    @DisplayName("Проверка успешного логина пользователя")
    public void checkSuccessUserLogin(){
        ValidatableResponse response = UserClient.loginUser(UserCredentials.from(user));
        accessToken = response.extract().path("accessToken");
        assertThat("Код ответа некорректный", response.extract().statusCode(), equalTo(200));
    }
}
