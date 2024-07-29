package Steps;

import Body.RegUser;
import Models.UsersReg;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserRenameStep {
    public static final Gson gson = new Gson();
    private static final String RENAME_ENDPOINT = "/api/auth/user";
   /*private static RegUser regUser;*/

    @Step("Create User")
    public void UserRegStep (UsersReg newUser, String errorMessage) {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(newUser))
                .when()
                .post("/api/auth/register");
        String errorMessage1 = response.jsonPath().getString("message");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        response.then().log().all()
                .statusCode(200);
    }

    @Step("Login User")
    public String UserLoginStep (UsersReg UsersReg) {
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

    @Step("rename User with auth")
    public void UserRenameWithAuthStep (UsersReg userRenameEmail, RegUser regUser) {
        Response patch = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", regUser.getAccessToken())
                .body(gson.toJson(userRenameEmail))
                .when()
                .patch(RENAME_ENDPOINT);
        patch.then().log().all()// Логирование всего ответа
                .statusCode(200);
    }

    @Step("rename User whithout auth")
    public void UserRenameWhithoutAuthStep (UsersReg newUser, RegUser regUser) {
        Response patch = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(newUser))
                .when()
                .patch(RENAME_ENDPOINT);
        patch.then().log().all()// Логирование всего ответа
                .statusCode(401);
    }

    @Step("Delete User")
    public static void DeleteUser(UsersReg UsersReg, RegUser regUser, String accessToken) {
        /*String errorMessage = response.jsonPath().getString("message");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken().replace("Bearer ", "");*/
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
