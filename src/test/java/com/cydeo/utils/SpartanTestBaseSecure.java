package com.cydeo.utils;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class SpartanTestBaseSecure {
    @BeforeAll//@beforeClass in junit4
    public static void setUp(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.secure.api.url");
    }
}
