import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class User {

    public String name;
    public String password;
    public String email;

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public User(){
    }

    public User(String name, String password, String email){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public static User generateRandomUser(){
        final String name = RandomStringUtils.randomAlphabetic(10);
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String email = (RandomStringUtils.randomAlphabetic(13) + EMAIL_POSTFIX).toLowerCase();
        return new User(name,password,email);
    }


  /*  public Map<String, String> registerRandomUser(){

        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);

        // создаём и заполняем мапу для передачи трех параметров в тело запроса
        Map<String, String> inputDataMap = new HashMap<>();
        inputDataMap.put("email", email);
        inputDataMap.put("password", password);
        inputDataMap.put("name", name);

        // отправляем запрос на регистрацию пользователя и десериализуем ответ в переменную response
        UserRegisterResponse response = given()
                .spec(BurgerRestClient.getBaseSpec())
                .and()
                .body(inputDataMap)
                .when()
                .post("auth/register")
                .body()
                .as(UserRegisterResponse.class);

        Map<String, String> responseData = new HashMap<>();
        if (response != null) {
            responseData.put("email", response.getUser().getEmail());
            responseData.put("name", response.getUser().getName());
            responseData.put("password", password);

            Tokens.setAccessToken(response.getAccessToken().substring(7));
            Tokens.setRefreshToken(response.getRefreshToken());
        }
        return responseData;
    }
*/
    public User setName(String name){
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setPassword(String password){
        this.password = password;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setEmail(String email){
        this.email = email;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public String getDataField(String field) {
        switch (field) {
            case ("name"):
                return name;
            case ("email"):
                return email;
            case ("password"):
                return password;
        }
        return field;
    }

    public User withoutPassword(){
        return new User()
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setEmail(RandomStringUtils.randomAlphabetic(10)+EMAIL_POSTFIX);
    }

    public User withoutName(){
        return new User()
                .setPassword(RandomStringUtils.randomAlphabetic(10))
                .setEmail(RandomStringUtils.randomAlphabetic(10)+EMAIL_POSTFIX);
    }

    public User withoutEmail(){
        return new User()
                .setPassword(RandomStringUtils.randomAlphabetic(10))
                .setName(RandomStringUtils.randomAlphabetic(10));
    }

} 