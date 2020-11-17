// Configuration class for Form Automation project

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class Utils {
        static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";
        static final String JSON_USER_ELEVEN = "\n" +
                "     {\n" +
                "         \"id\": 11,\n" +
                "        \"name\": \"Krishna Rungta\",\n" +
                "         \"username\": \"Bret\",\n" +
                "         \"email\": \"Sincere@april.biz\",\n" +
                "         \"address\": {\n" +
                "             \"street\": \"Kulas Light\",\n" +
                "             \"suite\": \"Apt. 556\",\n" +
                "             \"city\": \"Gwenborough\",\n" +
                "             \"zipcode\": \"92998-3874\",\n" +
                "             \"geo\": {\n" +
                "                 \"lat\": \"-37.3159\",\n" +
                "                 \"lng\": \"81.1496\"\n" +
                "             }\n" +
                "         },\n" +
                "         \"phone\": \"1-770-736-8031 x56442\",\n" +
                "         \"website\": \"hildegard.org\",\n" +
                "         \"company\": {\n" +
                "             \"name\": \"Romaguera-Crona\",\n" +
                "             \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "             \"bs\": \"harness real-time e-markets\"\n" +
                "         }\n" +
                "     }\n";

        public static int returnResponseStatus(String URL){
                return given().
                        when().
                        get(URL).
                        getStatusCode();
        }
        public static ArrayList<String> returnJSONElements(String URL, String elementName){
                 ArrayList<String> emails =
                        given().when().get(URL).then().extract().jsonPath().get(elementName) ;
                 return emails;
        }
}

