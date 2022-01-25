import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ChangeAuthUserDataTest {
        public UserClient userClient;
        public String accessToken;

        @Before
        public void setUp() {
            userClient = new UserClient();
        }

        @After
        public void tearDown() {
            if (accessToken == null) {
                return;
            }
            UserClient.delete(accessToken.substring(7));
        }

        @Test
        @DisplayName("Обновление электронной почты, пользователь авторизован")
        public void editEmailAuthUserTest() {
            User user = User.generateRandomUser();
            accessToken = userClient.createUser(user).extract().path("accessToken");
            UserCredentials newEmailBody = UserCredentials.withNewEmail(user);

            ValidatableResponse response = userClient.editDataWithAuth(newEmailBody, accessToken);
            HashMap<String, String> userInfo = response.extract().path("user");

            Assert.assertEquals(newEmailBody.email, userInfo.get("email"));
            Assert.assertEquals(200, response.extract().statusCode());
            Assert.assertTrue(response.extract().path("success"));
        }

        @Test
        @DisplayName("Обновление имени, пользователь авторизован")
        public void editNameAuthUserTest() {
            User user = User.generateRandomUser();
            accessToken = userClient.createUser(user).extract().path("accessToken");
            UserCredentials newNameBody = UserCredentials.withNewName(user);

            ValidatableResponse response = userClient.editDataWithAuth(newNameBody, accessToken);
            HashMap<String, String> userInfo = response.extract().path("user");

            Assert.assertEquals(newNameBody.name, userInfo.get("name"));
            Assert.assertEquals(200, response.extract().statusCode());
            Assert.assertTrue(response.extract().path("success"));
        }


        @Test
        @DisplayName("Обновление пароля, пользователь авторизован")
        public void editPasswordAuthUserTest() {
            User user = User.generateRandomUser();
            accessToken = userClient.createUser(user).extract().path("accessToken");
            UserCredentials newPasswordBody = UserCredentials.withNewPassword(user);

            ValidatableResponse editResponse = userClient.editDataWithAuth(newPasswordBody, accessToken);
            Assert.assertEquals(200, editResponse.extract().statusCode());

            ValidatableResponse response = UserClient.loginUser(new UserCredentials(user.name, newPasswordBody.password, user.email));
            Assert.assertEquals(200, response.extract().statusCode());
            Assert.assertTrue(response.extract().path("success"));
            Assert.assertNull(response.extract().path("message"));
    }
}