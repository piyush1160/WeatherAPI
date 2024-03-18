package com.test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author piyushbhatia
 */

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;

public class MainWeather {
    Float tempinCelcius;
    int humidity;
    String descp;
    JsonPath jsonresponse;

    public String testWeatherApi( String city){
        RestAssured.baseURI= "http://api.openweathermap.org/";
        String response = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8").get("data/2.5/weather")
                .then().log().all()
                .assertThat()
                .statusCode(200).extract().response().asString();

        System.out.println(response);

        jsonresponse = new JsonPath(response);
        Float temp  = jsonresponse.get("main.temp");
        tempinCelcius = (float) (temp - 273.15);
        System.out.println(tempinCelcius);
        humidity  = jsonresponse.get("main.humidity");
        System.out.println(humidity);
        descp = jsonresponse.get("weather[0].description");
        System.out.println(descp);

        return "Description : "+ descp + "\nTemperature : "+ tempinCelcius + " Celsius\nHumidity : "+ humidity + "%"   ;
    }

    public String testWeatherApi003( String city){
        RestAssured.baseURI= "http://api.openweathermap.org/";
        String response = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8").get("data/2.5/weather")
                .then().log().all()
                .assertThat()
                .statusCode(404).extract().response().asString();



        return "Description : "+ descp + "\nTemperature : "+ tempinCelcius + " Celsius\nHumidity : "+ humidity + "%"   ;
    }


    public Response testWeatherApi002(String city){
        RestAssured.baseURI= "http://api.openweathermap.org/";
        Response response = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8").get("data/2.5/weather")
                .then().log().all()
                .assertThat()
                .statusCode(404).extract().response();

//        System.out.println(response);
//
//        jsonresponse = new JsonPath(response);
//        Float temp  = jsonresponse.get("main.temp");
//        tempinCelcius = (float) (temp - 273.15);
//        System.out.println(tempinCelcius);
//        humidity  = jsonresponse.get("main.humidity");
//        System.out.println(humidity);
//        descp = jsonresponse.get("weather[0].description");
//        System.out.println(descp);

      return response;
    }
}

