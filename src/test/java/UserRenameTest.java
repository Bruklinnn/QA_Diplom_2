import Body.RegUser;
import Models.UsersReg;
import Steps.UserRenameStep;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class UserRenameTest extends LinkTest{
    private UsersReg userNewLogin;
    private UserRenameStep userRenameStep;
    private UsersReg userRenameEmail;
    private UsersReg userRenameName;
    private UsersReg userRenamePassword;
    private UsersReg newUser;
    private boolean userCreated;

    @Before
    public void setUp() {
        userRenameStep = new UserRenameStep();
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
                UserRenameStep.DeleteUser(userNewLogin,accessToken);
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
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        userRenameStep.UserRenameWithAuthStep(userRenameEmail, regUser);
        userCreated = true;
    }

    @Test
    public void UserRenameEmailWithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenameEmail);
    }

    @Test
    public void UserRenamePasswordWhithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenamePassword);
    }

    @Test
    public void UserRenameNameWhithoutAuthTest() {
        userRenameStep.UserRenameWhithoutAuthStep(userRenameName);
    }

}
