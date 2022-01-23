import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class IngredientsClient extends StellarBurgerRestClient {

    @Step
    public ValidatableResponse getAllIngredients (){
        return given()
                .spec(getBaseSpec())
                .when()
                .get("/ingredients")
                .then();
    }
}
