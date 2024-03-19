/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test;

import com.mycompany.weather.Weather;
import org.testng.annotations.Test;

/**
 *
 * @author piyushbhatia
 */
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.json.exception.JsonPathException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class WeatherTest {

    Float tempinCelcius;
    int humidity;
    String descp;
    JsonPath jsonresponse;

    MainWeather mobj = new MainWeather();
     @Test(priority = 1)
    public void testFetchWeatherData() {
        String weatherInfo = mobj.testWeatherApi("London");
        Assert.assertNotNull(weatherInfo);
        Assert.assertTrue(weatherInfo.contains("Description"));
        Assert.assertTrue(weatherInfo.contains("Temperature"));
        Assert.assertTrue(weatherInfo.contains("Humidity"));
    }


    // So, String temperature = weatherInfo.split("\n")[1]; means “get the second line of the weatherInfo string and store it in the temperature variable”.
    @Test(priority = 2)
    public void testTemperature() {
        String weatherInfo =  mobj.testWeatherApi("London");
        String temperature = weatherInfo.split("\n")[1];
        Assert.assertTrue(temperature.contains("Celsius"));
    }

        @Test(priority = 3)
    public void testHumidity() {
        String weatherInfo = mobj.testWeatherApi("London");
        String humidity = weatherInfo.split("\n")[2];
        Assert.assertTrue(humidity.contains("%"));
    }

    @Test(priority = 4)
    public void testFetchWeatherData_ValidCity() {
        String weatherInfo = mobj.testWeatherApi("London");
        Assert.assertFalse(weatherInfo.contains("temp"), "Temperature in Celsius is  present in the response");
        Assert.assertFalse(weatherInfo.contains("humidity"), "Humidity is  present in the response");
    }

    @Test(priority = 5)
    public void testFetchWeatherData_InvalidCity() throws JsonPathException {
        Response weatherInfo =mobj.testWeatherApi002("xvn");
        JsonPath json = new JsonPath("weatherInfo");
        String expect = json.getString("message");
        Assert.assertEquals(weatherInfo, "city not found", "The response is not as expected for an invalid city");

        }

    @Test(priority = 6)
    public void testFetchWeatherData_EmptyCity() {
        Response weatherInfo = mobj.testWeatherApi002(" ");
       // Assert.assertEquals(weatherInfo, "city not found", "The response is not as expected for an empty city");
        weatherInfo.then().log().all().assertThat().statusCode(404).body(matchesJsonSchemaInClasspath("Schema2.json"));


    }

}

