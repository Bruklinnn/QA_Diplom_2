import Body.RegUser;
import Models.UsersReg;
import Steps.UserLoginStep;
import Steps.UserRegStep;
import Steps.UserRenameStep;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class UserRenameTest extends LinkTest{
    private UserLoginStep userLoginStep;
    private UsersReg userNewLogin;
    private RegUser regUser;
    private UserRenameStep userRenameStep;
    private UsersReg userRenameEmail;
    private UsersReg userRenameName;
    private UsersReg userRenamePassword;
    private UsersReg newUser;
    private String accessToken;
    private boolean userCreated;

    @Before
    public void setUp() {
        userRenameStep = new UserRenameStep();
        UsersReg userLogin = new UsersReg("desto@mail.ru", "desto123");
        userNewLogin = new UsersReg("vesto@mail.ru", "desto123");
        newUser = new UsersReg("desto@mail.ru", "desto123", "desto");
        userRenameEmail = new UsersReg("vesto@mail.ru","", "");
        userRenameName = new UsersReg("", "", "vesto");
        userRenamePassword = new UsersReg("", "vesto123", "");
        userCreated = false;
    }

    @After
    public void tearDown() {
        if (userCreated) {
           String accessToken = userRenameStep.UserLoginStep(userNewLogin);
            if (accessToken != null) {
                UserRenameStep.DeleteUser(userNewLogin,regUser,accessToken);
            }
        }
    }


    @Test
    public void UserRenameWithAuth() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(newUser))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        String errorMessage = response.jsonPath().getString("message");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        userRenameStep.UserRenameWithAuthStep(userRenameEmail, regUser);
        userCreated = true;
    }

    @Test
    public void UserRenameEmailWithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenameEmail, regUser);
    }

    @Test
    public void UserRenamePasswordWhithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenamePassword, regUser);
    }

    @Test
    public void UserRenameNameWhithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenameName, regUser);
    }


    /*userRenameStep.UserRenameTest(userRenameEmail);*/
    /*@Test
    public void DeleteUser() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(usersNewReg))
                .when()
                .post("/api/auth/login");
        String errorMessage = response.jsonPath().getString("message");
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken().replace("Bearer ", "");
        Response delete = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", regUser.getAccessToken())
                .body(gson.toJson(usersNewReg))
                .delete("/api/auth/user");
        delete.then().log().all()// Логирование всего ответа
                .statusCode(202);
    }*/

}
