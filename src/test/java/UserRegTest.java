import Models.UsersReg;
import Steps.UserRegStep;
import Steps.UserRenameStep;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class UserRegTest extends LinkTest {
    private UserRegStep userRegStep;
    private UsersReg usersNewReg;
    private UsersReg usersRegWithoutPassword;
    private UsersReg usersRegWithoutEmail;
    private UsersReg usersRegWithoutName;
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
                UserRenameStep.DeleteUser(usersNewReg,accessToken);
            }
        }
    }

    @Test
    @DisplayName("NewReg")
    public void testUserReg() {
        userRegStep.UserRegStepTest(usersNewReg);
        userCreated = true;
    }

    @Test
    @DisplayName("NewRegAndExistingEmail")
    public void testUserRegAndExistingEmail() {
        userRegStep.UserRegStepTest(usersNewReg);
        userRegStep.UserWithExistingEmail(usersNewReg);
        userCreated = true;
    }

    @Test
    @DisplayName("RegWithoutPassword")
    public void testUserRegWithoutPassword() {
        userRegStep.UserRegWithoutPassword(usersRegWithoutPassword);
        System.out.println("Email, password and name are required fields");
    }

    @Test
    @DisplayName("RegWithoutEmail")
    public void testUserRegWithoutEmail() {
        userRegStep.UserRegWithoutEmail(usersRegWithoutEmail);
        System.out.println("Email, password and name are required fields");
    }

    @Test
    @DisplayName("RegWithoutName")
    public void testUserRegWithoutName() {
        userRegStep.UserRegWithoutName(usersRegWithoutName);
        System.out.println("Email, password and name are required fields");
    }
    }




