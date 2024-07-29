import io.restassured.RestAssured;
import org.junit.Before;

public class LinkTest {
    @Before
    public void setupRestAssured() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }
}
