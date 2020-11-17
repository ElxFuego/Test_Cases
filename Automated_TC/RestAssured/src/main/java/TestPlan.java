import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class TestPlan {

    @Test(testName = "Get response status after request")
    public static void getResponseStatus(){
        System.out.println("The response status is " + Utils.returnResponseStatus(Utils.BASE_URL));
        given().when().get(Utils.BASE_URL).then().assertThat().statusCode(200);
    }
    @Test(testName = "Check number of users")
    public static void getNumberOfUsers() {
        ArrayList<String> users =
                Utils.returnJSONElements(Utils.BASE_URL, "username");
        System.out.print("Number of users is : ");
        System.out.println(users.size());
        System.out.println("Usernames: ");
        for (String u : users) {
            System.out.println("-" + u);
        }
    }
    @Test(testName = "Check if certain email exists")
    public static void checkIfEmailExists(){

        final String baseEmail = "Sincere@april.biz";
        ArrayList<String> emails =
                Utils.returnJSONElements(Utils.BASE_URL, "email");
        String email = "";

        for (String s : emails) {
            if(s.equals(baseEmail))
            {
                email = s;
                assertThat(email).isEqualTo(baseEmail);
                break;
            }
        }
        System.out.println("Email " + baseEmail + " exists");
        ArrayList<String> username =  Utils.returnJSONElements(Utils.BASE_URL + "?email=" + email, "username");
        assertThat(username.size()).isEqualTo(1);
        System.out.println("It belongs to username " + username);
    }
    @Test(testName = "Create new user")
    public static void createNewUser(){
        given().contentType(ContentType.JSON).body(Utils.JSON_USER_ELEVEN).post(Utils.BASE_URL).
                then().statusCode(201).log().body();
       System.out.println("-----User created-----");
    }
}
