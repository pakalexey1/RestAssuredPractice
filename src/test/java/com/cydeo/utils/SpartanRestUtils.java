package com.cydeo.utils;
import static io.restassured.RestAssured.*;

public class SpartanRestUtils {

    private static String baseUrl = ConfigurationReader.getProperty("spartan.api.url");

    public static void deleteSpartanById(int spartanId) {
        // send DELETE request {{baseUrl}}/api/spartans/{id}
        given().pathParam("id", spartanId)
                .when().delete(baseUrl + "/{id}")
                .then().log().all();
    }

    //it's possible to create post, update methods
}
