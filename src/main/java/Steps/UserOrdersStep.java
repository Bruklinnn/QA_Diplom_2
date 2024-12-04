package Steps;

import Body.RegUser;
import Models.UsersReg;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class UserOrdersStep {
    public static final Gson gson = new Gson();
    private static final String ORDER_ENDPOINT = "/api/orders";

    @Step("Create User")
    public static void UserRegStepTest (UsersReg UsersReg) {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/register");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        response.then().log().all();
    }

    @Step("Login User")
    public static String UserLoginStepTest(UsersReg UsersReg) {
        Response response = RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/login");
        response.then().log().all()
                .statusCode(200);
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        return regUser.getAccessToken();
    }

    @Step("Delete User")
    public static void DeleteUser(UsersReg UsersReg, String accessToken) {
        Response delete = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(gson.toJson(UsersReg))
                .delete("/api/auth/user");
        delete.then().log().all()// Логирование всего ответа
                .statusCode(202);
    }

    @Step("Order with auth")
    public static void OrderWithAuth(String accessToken) {
        String FluorescentBurger = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6d\"]}";
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(FluorescentBurger)
                .post(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(200);
    }

    @Step("Order without auth")
    public static void OrderWithoutAuth () {
        String FluorescentBurger = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6d\"]}";
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(FluorescentBurger)
                .post(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(200);
    }
    @Step("Order with auth and ingridients")
    public static void OrderWithAuthAndIngridients (String accessToken) {
        String TheImmortalFluorescentBurger = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\", \"61c0c5a71d1f82001bdaaa72\"]}";
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(TheImmortalFluorescentBurger)
                .post(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(200);
    }
    @Step("Order without ingridients")
    public static void OrderWithoutIngridients (String accessToken) {
        String TheImmortalFluorescentBurger = "{\"ingredients\":[]}";
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(TheImmortalFluorescentBurger)
                .post(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(400);
    }
    @Step("Order with invalid hash")
    public static void OrderWithInvalidHash (String accessToken) {
        String TheImmortalFluorescentBurger = "{\"ingredients\":[\"61c0c5a71d1f82001bdaq212da63\", \"61c0c5a71d1f82001bdaaa62\"]}";
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(TheImmortalFluorescentBurger)
                .post(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(500);
    }
}
