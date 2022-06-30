package utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ReusableMethods {

    public static String tokenAlma(){
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("password", "Cw192837?");
        requestBody.put("rememberMe", false);
        requestBody.put("username", "CWoburn");

        Response response = RestAssured.
                given().
                headers("Content-Type", ContentType.JSON).
                when().
                body(requestBody).
                post("https://gmibank.com/api/authenticate");

        //response.prettyPrint();

        // 31. satirin alternatif cozumu
//        JsonPath jsonPath = response.jsonPath();
//        return jsonPath.getString("id_token");
        return response.jsonPath().getString("id_token");
    }

}
