package com.cydeo.streampractice.practice;

import com.cydeo.streampractice.bootstrap.DataGenerator;
import com.cydeo.streampractice.service.CountryService;
import lombok.Data;

public class TestMine {
    public static void main(String[] args) {

//        System.out.println(Practice.getAllCountryNames());
       // DataGenerator data = new DataGenerator();

        System.out.println(Practice.getAllDepartmentsWhereRegionOfCountryIsEurope());


    }
}
