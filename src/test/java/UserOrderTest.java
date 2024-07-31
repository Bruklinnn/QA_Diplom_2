import Body.RegUser;
import Models.UsersReg;
import Steps.UserOrdersStep;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class UserOrderTest extends LinkTest {
    private UsersReg userForOrder;
    private boolean userCreated;


    @Before
    public void setUp() {
        userForOrder = new UsersReg("Reg@mail.ru", "Reg1234", "Reg");
        userCreated = false;
    }

    @After
    public void tearDown() {
        if (userCreated) {
            String accessToken = UserOrdersStep.UserLoginStepTest(userForOrder);
            if (accessToken != null) {
                UserOrdersStep.DeleteUser(userForOrder, accessToken);
            }
        }
    }

    @Test
    public void OrderWithAuthTest() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(userForOrder))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        UserOrdersStep.OrderWithAuth(accessToken);
        userCreated = true;
    }

    @Test
    public void OrderWithoutAuthTest() {
        UserOrdersStep.OrderWithoutAuth();
    }

    @Test
    public void OrderWithAuthAndIngridientsTest() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(userForOrder))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        UserOrdersStep.OrderWithAuthAndIngridients(accessToken);
        userCreated = true;
    }

    @Test
    public void OrderWithoutIngridientsTest() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(userForOrder))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        UserOrdersStep.OrderWithoutIngridients(accessToken);
        userCreated = true;
    }
    @Test
    public void OrderWithInvalidHashTest() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(userForOrder))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        UserOrdersStep.OrderWithInvalidHash(accessToken);
        userCreated = true;
    }
}
