import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends StellarBurgerRestClient {

    @Step
    public ValidatableResponse createOrderWithAuth(IngredientsData ingredients, String bearerToken) {
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post("/orders")
                .then();
    }

    @Step
    public ValidatableResponse createOrderWOAuth(IngredientsData ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post("/orders")
                .then();
    }

    @Step
    public ValidatableResponse getOrdersNonAuth() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/orders")
                .then();
    }

    @Step
    public ValidatableResponse getOrdersAuth(String bearerToken) {
        return given()
                .headers("Authorization", bearerToken)
                .spec(getBaseSpec())
                .when()
                .get("/orders")
                .then();
    }
}
