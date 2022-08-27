package com.cydeo.tests.day16_methodsource_mocks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;

public class MethodSourceTest {
    /**
      data provider method
     */
    public static List<String> getCountries() {
        List<String> countries = Arrays.asList("Canada", "USA", "France", "Turkey", "Mexico");
        return countries;
    }

    @ParameterizedTest
    @MethodSource("getCountries")
    public void countriesTest(String countryName){
        System.out.println("Country name = " + countryName);
        System.out.println("countryName.length() = " + countryName.length());

    }
}
