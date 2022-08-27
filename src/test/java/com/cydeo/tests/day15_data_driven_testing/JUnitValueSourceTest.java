package com.cydeo.tests.day15_data_driven_testing;

import com.cydeo.utils.ConfigurationReader;
import joptsimple.internal.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.cydeo.pojo.POSTRegion;
import com.cydeo.pojo.Spartan;
import com.cydeo.tests.day04_path_jsonpath.HRApiGetTest;
import com.cydeo.utils.*;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.meta.When;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JUnitValueSourceTest {

    @ParameterizedTest
    @ValueSource(ints = {5, 23, 90, 33, 64, 10986, 456})
    public void numberTest(int num) {
        System.out.println("num = " + num);
        assertThat(num, is(greaterThan(0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Vugar", "Shina", "Dzerassa", "Eda", "Kevin", "", "Nadir"})
    public void testNames(String name) {
        System.out.println("Names = " + name);
        assertThat(name, not(blankOrNullString()));
    }

    @BeforeAll
    public static void setUp (){
        baseURI = ConfigurationReader.getProperty("zipcode.api.url");
    }

    @ParameterizedTest
    @ValueSource(ints = {22102, 22031, 22034, 22012, 90027})
    public void zipCodeTest(int zipcode){
        given().accept(ContentType.JSON)
                .and().pathParam("postal-code",zipcode)
                .when().get("/us/{postal-code}")
                .then().assertThat().statusCode(200)
                .log().all();

    }
}
