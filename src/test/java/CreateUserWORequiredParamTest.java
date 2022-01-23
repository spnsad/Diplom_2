import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserWORequiredParamTest {

    private final User missingParam;
    private final Integer expectedRespCode;
    public final String missingParamName;


    public CreateUserWORequiredParamTest(User missingParam , int expectedRespCode, String missingParamName) {
        this.missingParam = missingParam;
        this.expectedRespCode = expectedRespCode;
        this.missingParamName = missingParamName;
    }

    @Parameterized.Parameters(name = "Регистрация {2}. Ожидаемый код ответа - {1}")
    public static Object[][] missingParameters() {

        return new Object[][] {
                {new User().withoutName(), 403, "без имени"},
                {new User().withoutPassword(), 403, "без пароля"},
                {new User().withoutEmail(), 403, "без электронной почты"}
        };
    }

    @Test
    public void CreateUserWORequiredParam(){
        ValidatableResponse response = new UserClient().createUser(missingParam);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("User is registered, it's not correct", errorMessage, equalTo("Email, password and name are required fields"));
        assertThat("Status code is incorrect", statusCode, equalTo(expectedRespCode));
    }
}
