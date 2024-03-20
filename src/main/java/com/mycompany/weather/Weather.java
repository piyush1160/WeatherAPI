///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// */
//
package com.mycompany.weather;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import static org.hamcrest.Matchers.*;

import java.awt.event.*;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.JSONValue;
import org.testng.annotations.Test;


import javax.swing.*;
import java.awt.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Weather {

    private static JFrame frame;
    private static JTextField locationFeild;
    private static JTextArea weatherDisplay;
    private static JButton fetchButton;

    private static final String apiKey = "c71a3c0964298436fd10d0c75736dff8";

    private static String fetchingResponse(String city) {

        try {

            if (city.isEmpty()) {
                // Show a popup message
                JOptionPane.showMessageDialog(null, "Please enter city.", "Error", JOptionPane.ERROR_MESSAGE);
                return " ";
            }
            RestAssured.baseURI = "http://api.openweathermap.org/";

            Response response1 = given().queryParam("q", city).queryParam("appid", "c71a3c0964298436fd10d0c75736dff8")
                    .get("data/2.5/weather")
                    .then().log().all()
                    .assertThat().extract().response();

            int statusCode = response1.getStatusCode();
            int ExpectedCode = 200;
            System.out.println(statusCode);
            ResponseBody body = response1.getBody();

            String respString = response1.asString();
            System.out.println(respString);

            System.out.println("Response is" + response1);

            if (statusCode == ExpectedCode) {

                JsonPath jsonresponse = new JsonPath(respString);
                Float temp = jsonresponse.get("main.temp");
                Float tempinCelcius = (float) (temp - 273.15);
                System.out.println(tempinCelcius);
                int humidity = jsonresponse.get("main.humidity");
                System.out.println(humidity);
                String descp = jsonresponse.get("weather[0].description");
                System.out.println(descp);

                return "Description : " + descp + "\nTemperature : " + tempinCelcius + " Celsius\nHumidity : " + humidity + "%";

            } else {
                JOptionPane.showMessageDialog(null, "Please enter valid city.", "Error", JOptionPane.ERROR_MESSAGE);
                return "Invalid city";

            }

        } catch (Exception e) {
           
            System.out.println(e);
            return "";
        }

    }

    private static String fetchWeatherData(String city) {
        try {

            if (city.isEmpty()) {
                // Show a popup message
                JOptionPane.showMessageDialog(null, "Please enter city.", "Error", JOptionPane.ERROR_MESSAGE);
                return " ";
            }

            RestAssured.baseURI = "http://api.openweathermap.org/";
            String response1 = given().queryParam("q", city).queryParam("appid", "c71a3c0964298436fd10d0c75736dff8")
                    .get("data/2.5/weather")
                    .then().log().all()
                    .assertThat().extract().response().asString();


            System.out.println("Response is" + response1);

            JsonPath jsonresponse = new JsonPath(response1);
            Float temp = jsonresponse.get("main.temp");
            Float tempinCelcius = (float) (temp - 273.15);
            System.out.println(tempinCelcius);
            int humidity = jsonresponse.get("main.humidity");
            System.out.println(humidity);
            String descp = jsonresponse.get("weather[0].description");
            System.out.println(descp);

            // 1. fetching full resonsewhen city is valid then status code is 200.
            // 2. 
            String responseCode = jsonresponse.getString("cod");
            System.out.println("responsecode: " + responseCode);

            String responseMessage = jsonresponse.getString("message");
            System.out.println("responseMessage: " + responseMessage);
            String statusCode = "200";

            if (responseCode.equals(statusCode)) {
              
                System.out.println("successfully run");
            } else {
                RestAssured.baseURI = "http://api.openweathermap.org/";
                Response response2 = given()
                        .queryParam("q", "ABC")
                        .queryParam("appid", "c71a3c0964298436fd10d0c75736dff8")
                        .when()
                        .get("http://api.openweathermap.org/data/2.5/weather")
                        .then()
                        .log().all()
                        .assertThat()
                        // Assuming you're expecting a successful response
                        .extract()
                        .response();
                System.out.println(response2);

                String name = response2.jsonPath().getString("cod");
                System.out.println(name);
            }

            return "Description : " + descp + "\nTemperature : " + tempinCelcius + " Celsius\nHumidity : " + humidity + "%";
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter valid city.", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
        }

    }

    
    
    
    
    public static void setFrame(){
          frame = new JFrame("KeyForecast");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new FlowLayout());

        locationFeild = new JTextField(15);
        fetchButton = new JButton("Fetch weather");
        weatherDisplay = new JTextArea(12, 30);
        weatherDisplay.setEditable(false);

        // display everything 
        frame.add(new JLabel("Enter City Name"));
        frame.add(locationFeild);
        frame.add(fetchButton);
        frame.add(weatherDisplay);

       
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 250));
        fetchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String city = locationFeild.getText();

                //String weatherInfo = fetchWeatherData(city);
                String weatherInfo = fetchingResponse(city);
                weatherDisplay.setText(weatherInfo);

            }
        });

        frame.setVisible(true);

        }
    
    
    
    
    
    // launch the frame ... 
    public static void main(String[] args) {
        setFrame();
        
    }
}
