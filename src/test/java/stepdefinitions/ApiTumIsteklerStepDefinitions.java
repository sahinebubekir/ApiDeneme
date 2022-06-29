package stepdefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.ConfigReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static utilities.ConfigReader.getProperty;
import static utilities.ReusableMethods.tokenAlma;

public class ApiTumIsteklerStepDefinitions {

    Response response;
    String loginName;

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
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("address", "Almanya");
        requestBody.put("email", new Faker().internet().emailAddress());
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
    }

    @And("Kullanicinin bilgileri guncellenir")
    public void kullanicininBilgileriGuncellenir() {
    }

    @And("Kullanici silinir")
    public void kullaniciSilinir() {
    }
}
