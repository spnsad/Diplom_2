import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends StellarBurgerRestClient {

    @Step
    public ValidatableResponse createUser(User user){
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post("/auth/register")
                .then();
    }

    @Step
    public static void delete(String accessToken) {
        given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete("/auth/user")
                .then()
                .statusCode(202);
    }

    @Step
    public static ValidatableResponse loginUser(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post("/auth/login")
                .then();
    }

    @Step
    public static ValidatableResponse logoutUser(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post("/auth/logout")
                .then();
    }

    @Step
    public ValidatableResponse editDataWithAuth(UserCredentials userData, String bearerToken) {
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .patch( "/auth/user")
                .then();
    }

    @Step
    public ValidatableResponse editDataWOAuth(UserCredentials userData) {
        return given()
                .spec(getBaseSpec())
                .body(userData)
                .when()
                .patch("/auth/user")
                .then();
    }

    @Step
    public ValidatableResponse getInfo(String accessToken){
        return given()
                .header("Authorization", accessToken)
                .spec(getBaseSpec())
                .when()
                .get("/auth/user")
                .then();




    }
}
