package com.cydeo.utils;

import com.cydeo.pojo.Spartan;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class SpartanRestUtils {

    private static String baseUrl = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanId) {
        // send DELETE request {{baseUrl}}/api/spartans/{id}
        System.out.println("DELETE spartan with id {" + spartanId + "}");
        given().pathParam("id", spartanId)
                .when().delete(baseUrl + "/spartans/{id}")
                .then().log().all();
    }

    /**
     Generate a random spartan
     * @return is a POJO class
     */
    public static Spartan getNewSpartan() {
        Faker random = new Faker();
        Spartan spartan = new Spartan();
        spartan.setName(random.name().firstName());
        int num = random.number().numberBetween(1, 3); //3 is not included
        if (num == 1) {
            spartan.setGender("Female");
        } else {
            spartan.setGender("Male");
        }
        spartan.setPhone(random.number().numberBetween(1000000000L, 9999999999L));

        return spartan;
    }

    /**
     Method accepts spartanId and sends a GET request
     * @param spartanId
     * @return is a Map object that contains information about the spartanId
     */
    public static Map<String, Object> getSpartan(int spartanId) {
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", spartanId)
                .when().get(baseUrl + "/spartans/{id}");

        //return response.as(Map.class);
        Map<String, Object> spartanMap = response.as(Map.class);
        return spartanMap;
    }
}
