package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static utilities.ConfigReader.getProperty;
import static utilities.ReusableMethods.tokenAlma;

public class ApiTumIsteklerStepDefinitions {

    Response response;
    String loginName;
    Map<String, Object> requestBody = new HashMap<>();
    int id;
    String email;
    int i = 0;
    @Given("Kullanici olusturulur")
    public void kullaniciOlusturulur() {
        //
        //POST
        /*
        address: "Almanya"
email: "sahinebu543@yahoo.com"
firstName: "Api"
langKey: "en"
lastName: "Deneme"
login: "sahinebu543"
mobilePhoneNumber: "432-424-2342"
password: "Ebu123."
ssn: "876-28-4937"
         */
        loginName = new Faker().name().username();
        email = new Faker().internet().emailAddress();

        requestBody.put("address", "Almanya");
        requestBody.put("email", email);
        requestBody.put("firstName", "API");
        requestBody.put("langKey", "en");
        requestBody.put("lastName", new Faker().name().lastName());
        requestBody.put("login", loginName);
        requestBody.put("mobilePhoneNumber", "432-532-7578");
        requestBody.put("password", "Ebu123.");
        requestBody.put("ssn", new Faker().idNumber().ssnValid());

        System.out.println(loginName);

         response = given().
                headers("Content-Type", "application/json").
                body(requestBody).
                post("https://gmibank.com/api/register");

        response.prettyPrint();
    }

    @And("Kullanicinin verileri alinir {string}")
    public void kullanicininVerileriAlinir(String usersEndpoint) {

        //
        //Get
        //Token

        response = given().
//                headers("Content-Type", ContentType.JSON, "Authorization", "Bearer " + tokenAlma()).
        contentType(ContentType.JSON).
                        //Yukaridaki Header'sin icindeki Authorization Bearer kismi icin alternatif kullanim
                        auth().
                        oauth2(tokenAlma()).
                get(getProperty(usersEndpoint) + loginName);

        response.prettyPrint();

        if((i>2)){
            System.out.println(response.jsonPath().get("authorities").toString());
            id = response.jsonPath().getInt("id");
            System.out.println(response.jsonPath().getString("firstName"));
        }
        System.out.println(i);
        i++;
    }

    @And("Kullanicinin bilgileri {string} endpointi kullanilarak guncellenir {string}")
    public void kullanicininBilgileriGuncellenir(String endpoint, String guncellenicekRol) {
        /*
        https://gmibank.com/api/users
        PUT
        200

        activated: true
authorities: ["ROLE_CUSTOMER"]
email: "coleman.roberts@yahoo.com"
firstName: "API2345"
id: 161439
langKey: "en"
lastName: "Botsford"
login: "regan.marvin"
         */
        requestBody.clear();
        String []arr = {guncellenicekRol};

        // arr[0] "ROLE_CUSTOMER" ayni
        requestBody.put("activated", true);
        requestBody.put("authorities", arr);
        requestBody.put("email", email);
        requestBody.put("firstName", "API987654321");
        requestBody.put("id", id);
        requestBody.put("langKey", "en");
        requestBody.put("lastName", "Batsford24");
        requestBody.put("login", loginName);

        response = given().
                headers("Content-Type", ContentType.JSON).
                auth().
                oauth2(tokenAlma()).
                body(requestBody).
                put(getProperty(endpoint));

        response.prettyPrint();
    }

    @And("Kullanici silinir")
    public void kullaniciSilinir() {
/*

DELETE
 */
        response = given().
                headers("Content-Type", ContentType.JSON,
                        "Authorization", "Bearer " + tokenAlma()).
                delete("https://gmibank.com/api/users/" + loginName);

        response.prettyPrint();
    }
}
