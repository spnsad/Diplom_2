import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserWithIncorrectDataTest {
    String login = RandomStringUtils.randomAlphabetic(10);
    String password = RandomStringUtils.randomAlphabetic(10);
    String email = RandomStringUtils.randomAlphabetic(10);


    @Test
    @DisplayName("Проверка логина пользователя по несуществующим данным")
    public void checkLoginWithNonExistentData(){
        ValidatableResponse response = UserClient.loginUser(UserCredentials.nonRegistered(login, password, email));
        String errorMessage = response.extract().path("message");
        int statusCode = response.extract().statusCode();

        assertThat("Код ответа некорректный", statusCode, equalTo(401));
        assertThat("Тело ответа некорректное", errorMessage, equalTo("email or password are incorrect"));
    }
}
