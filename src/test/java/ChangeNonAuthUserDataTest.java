import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ChangeNonAuthUserDataTest {
    public UserCredentials body;
    public UserClient userClient;
    public String fieldName;

    public ChangeNonAuthUserDataTest(UserCredentials body, String fieldName) {
        this.body = body;
        this.fieldName = fieldName;
    }

    @Parameterized.Parameters(name = "Изменение {1} неавторизованного пользователя")
    public static Object[][] setUserData() {
        return new Object[][]{
                {new UserCredentials(RandomStringUtils.randomAlphabetic(10), null, null), "имени"},
                {new UserCredentials(null, (RandomStringUtils.randomAlphabetic(10)), null), "пароля"},
                {new UserCredentials(null, null, (RandomStringUtils.randomAlphabetic(10) + User.EMAIL_POSTFIX).toLowerCase()), "электронной почты"}
        };
    }

    @Step
    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    public void editNameEmailPasswordNotAuthUserParameterizedTest() {
        ValidatableResponse response = userClient.editDataWOAuth(body);
        Assert.assertEquals("You should be authorised", response.extract().path("message"));
        Assert.assertEquals(401, response.extract().statusCode());
        Assert.assertFalse(response.extract().path("success"));
    }
}