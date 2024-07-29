import Body.RegUser;
import Models.UsersReg;
import Steps.GetUserOrdersStep;
import Steps.UserOrdersStep;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static Steps.UserRegStep.gson;
import static io.restassured.RestAssured.given;

public class GetUserOrdersTest extends LinkTest {

    private UserOrdersStep userOrdersStep;
    private RegUser regUser;
    private UsersReg userForOrder;
    private String accessToken;
    private boolean userCreated;
    private String TheImmortalFluorescentBurger;


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
                UserOrdersStep.DeleteUser(userForOrder, regUser, accessToken);
            }
        }
    }

    @Test
    public void OrderListWithAuthTest() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(gson.toJson(userForOrder))
                .when()
                .post("/api/auth/register");
        response.then().log().all();
        RegUser regUser = gson.fromJson(response.getBody().asString(), RegUser.class);
        String accessToken = regUser.getAccessToken();
        GetUserOrdersStep.OrdersListWithAuth(regUser, accessToken);
        userCreated = true;
    }

    @Test
    public void OrderListWithoutAuthTest() {
        GetUserOrdersStep.OrdersListWithoutAuth(regUser, accessToken);
    }
}
