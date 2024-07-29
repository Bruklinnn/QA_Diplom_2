import Body.RegUser;
import Models.UsersReg;
import Steps.UserLoginStep;
import Steps.UserRegStep;
import Steps.UserRenameStep;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class UserLoginTest extends LinkTest {
    private RegUser regUser;
    private boolean userCreated;
    private UserLoginStep userLoginStep;
    private UsersReg userLogin;
    private UsersReg userBrokenEmail;
    private UsersReg userBrokenPassword;
    private UsersReg usersNewReg;
    private String accessToken;

    @Before
    public void setUp() {
        userCreated = false;
        userLoginStep = new UserLoginStep();
        Gson gson = new GsonBuilder().create();
        usersNewReg = new UsersReg("UserLog@mail.ru", "UserLog123", "ttesto");
        userLogin = new UsersReg("UserLog@mail.ru", "UserLog123");
        userBrokenEmail = new UsersReg("User@mail.ru", "UserLog123");
        userBrokenPassword = new UsersReg("UserLog@mail.ru", "testo123");
    }

    @After
    public void teardown() {
        if (userCreated) {
            String accessToken = userLoginStep.UserLoginStepTest(usersNewReg);
            if (accessToken != null) {
                UserRenameStep.DeleteUser(usersNewReg, regUser, accessToken);
            }
        }
    }

    @Test
    @DisplayName("Reg and Login User")
    public void UserLogin() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(usersNewReg))
                .when()
                .post("/api/auth/register");
        response.then().log().all()
                .statusCode(200);
       String accessToken = userLoginStep.UserLoginStepTest(userLogin);
        userCreated = true;
    }

    @Test
    @DisplayName("Login User")
    public void UserBrokenEmail() {
        userLoginStep.UserBrokenLoginStepTest(userBrokenEmail);
    }

    @Test
    @DisplayName("Login User")
    public void UserBrokenPassword() {
        userLoginStep.UserBrokenPasswordStepTest(userBrokenPassword);
    }
    }


