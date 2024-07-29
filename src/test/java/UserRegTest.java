import Body.RegUser;
import Models.UsersReg;
import Steps.UserLoginStep;
import Steps.UserRegStep;
import Steps.UserRenameStep;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class UserRegTest extends LinkTest {
    private RegUser regUser;
    private UserRegStep userRegStep;
    private UsersReg usersNewReg;
    private UsersReg usersRegWithoutPassword;
    private UsersReg usersRegWithoutEmail;
    private UsersReg usersRegWithoutName;
    private String accessToken;
    private boolean userCreated;


    @Before
    public void setUp() {
        userRegStep = new UserRegStep();
        usersNewReg = new UsersReg("Reg@mail.ru", "Reg1234", "Reg");
        usersRegWithoutPassword = new UsersReg("Reg@mail.ru", null, "Reg");
        usersRegWithoutEmail = new UsersReg(null, "Reg1234", "Reg");
        usersRegWithoutName = new UsersReg("Reg@mail.ru", "Reg1234", null);
        userCreated = false;

    }

    @After
    public void tearDown() {
        if (userCreated) {
            String accessToken = userRegStep.UserLoginStepTest(usersNewReg);
            if (accessToken != null) {
                UserRenameStep.DeleteUser(usersNewReg,regUser,accessToken);
            }
        }
    }

    @Test
    @DisplayName("NewReg")
    public void testUserReg() {
        userRegStep.UserRegStepTest(usersNewReg, regUser);
        userCreated = true;
    }

    @Test
    @DisplayName("NewRegAndExistingEmail")
    public void testUserRegAndExistingEmail() {
        userRegStep.UserRegStepTest(usersNewReg, regUser);
        userRegStep.UserWithExistingEmail(usersNewReg,regUser);
        userCreated = true;
    }

    @Test
    @DisplayName("RegWithoutPassword")
    public void testUserRegWithoutPassword() {
        userRegStep.UserRegWithoutPassword(usersRegWithoutPassword,regUser);
        System.out.println("Email, password and name are required fields");
    }

    @Test
    @DisplayName("RegWithoutEmail")
    public void testUserRegWithoutEmail() {
        userRegStep.UserRegWithoutEmail(usersRegWithoutEmail,regUser);
        System.out.println("Email, password and name are required fields");
    }

    @Test
    @DisplayName("RegWithoutName")
    public void testUserRegWithoutName() {
        userRegStep.UserRegWithoutName(usersRegWithoutName, regUser);
        System.out.println("Email, password and name are required fields");
    }
    }




