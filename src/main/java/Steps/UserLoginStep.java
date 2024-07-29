package Steps;

import Body.RegUser;
import Models.UsersReg;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserLoginStep {
    private static final Gson gson = new Gson();
    private static final String LOG_ENDPOINT = "/api/auth/login";


    @Step("Login User")
    public String UserLoginStepTest(UsersReg UsersReg) {
        Response response = RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post(LOG_ENDPOINT);
        response.then().log().all()
                .statusCode(200);
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        return regUser.getAccessToken();
    }

    @Step("Login Broken Email User")
    public void UserBrokenLoginStepTest(UsersReg UsersReg) {
        Response response = RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post(LOG_ENDPOINT);
        response.then().log().all()
                .statusCode(401);
    }
    @Step("Login Broken Password User")
    public void UserBrokenPasswordStepTest(UsersReg UsersReg) {
        Response response = RestAssured.given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post(LOG_ENDPOINT);
        response.then().log().all()
                .statusCode(401);
    }
    @Step("Delete User")
    public static void DeleteUser(UsersReg UsersReg, RegUser regUser, String accessToken) {

        /*String errorMessage1 = response.jsonPath().getString("message");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken1 = regUser.getAccessToken().replace("Bearer ", "");*/
        Response delete = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(gson.toJson(UsersReg))
                .delete("/api/auth/user");
        delete.then().log().all()// Логирование всего ответа
                .statusCode(202);
    }
}
