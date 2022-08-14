package com.cydeo.tests.day08_hamcrest;

import com.cydeo.pojo.Spartan;
import com.cydeo.utils.ConfigurationReader;
import com.cydeo.utils.SpartanRestUtils;
import com.cydeo.utils.SpartanTestBase;
import com.github.javafaker.Faker;

import static io.restassured.RestAssured.*;

public class SpartanPostThenGet extends SpartanTestBase {

    Spartan newSpartan = SpartanRestUtils.getNewSpartan();

}
