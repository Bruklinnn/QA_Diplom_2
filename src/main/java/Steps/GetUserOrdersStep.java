package Steps;
import Models.UsersReg;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GetUserOrdersStep {
    public static final Gson gson = new Gson();
    private static final String ORDER_ENDPOINT = "/api/orders";

    @Step("Delete User")
    public static void DeleteUser (UsersReg UsersReg, String accessToken) {
        Response delete = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(gson.toJson(UsersReg))
                .delete("/api/auth/user");
        delete.then().log().all()// Логирование всего ответа
                .statusCode(202);
    }

    @Step("User orders with auth")
    public static void OrdersListWithAuth(String accessToken) {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .get(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(200);
    }
    @Step("User orders without auth")
    public static void OrdersListWithoutAuth() {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .get(ORDER_ENDPOINT);
        response.then().log().all()// Логирование всего ответа
                .statusCode(401);
    }
}
