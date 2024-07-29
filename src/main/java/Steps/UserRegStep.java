package Steps;

import Body.RegUser;
import Models.UsersReg;
import com.google.gson.Gson;
;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class UserRegStep {
    public static final Gson gson = new Gson();
    private static final String REG_ENDPOINT = "/api/auth/register";



    @Step("Create User")
    public void UserRegStepTest(UsersReg UsersReg, RegUser regUser) {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(UsersReg))
                .when()
                .post(REG_ENDPOINT);
        RegUser regUser1 = gson.fromJson(response.getBody().asString(), RegUser.class);
        String errorMessage = response.jsonPath().getString("message");
        response.then().log().all()
                .statusCode(200);
    }

    @Step("Create User With Existing Email")
    public void UserWithExistingEmail(UsersReg UsersReg, RegUser regUser) {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/register");
        RegUser regUser1 = gson.fromJson(response.getBody().asString(), RegUser.class);
        String errorMessage = response.jsonPath().getString("message");
        response.then().log().all()// Логирование всего ответа
        .statusCode(403);
    }
    @Step("Create User Without Password")
    public void UserRegWithoutPassword(UsersReg UsersReg,RegUser regUser) {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/register");
        RegUser regUser1 = gson.fromJson(response.getBody().asString(), RegUser.class);
        String errorMessage = response.jsonPath().getString("message");
        response.then().log().all()// Логирование всего ответа
                .statusCode(403);
    }
    @Step("Create User Without Email")
    public void UserRegWithoutEmail(UsersReg UsersReg,RegUser regUser) {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/register");
        RegUser regUser1 = gson.fromJson(response.getBody().asString(), RegUser.class);
        String errorMessage = response.jsonPath().getString("message");
        response.then().log().all()// Логирование всего ответа
                .statusCode(403);
    }
    @Step("Create User Without Name")
    public void UserRegWithoutName(UsersReg UsersReg,RegUser regUser) {
        Response response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(gson.toJson(UsersReg))
                .when()
                .post("/api/auth/register");
        RegUser regUser1 = gson.fromJson(response.getBody().asString(), RegUser.class);
        String errorMessage = response.jsonPath().getString("message");
        response.then().log().all()// Логирование всего ответа
                .statusCode(403);
    }

    @Step("Login User")
    public String UserLoginStepTest(UsersReg UsersReg) {
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
    public static void DeleteUser(UsersReg UsersReg, RegUser regUser, String accessToken) {
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


