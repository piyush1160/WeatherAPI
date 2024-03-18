///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// */
//
package com.mycompany.weather;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
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
//
///**
// *
// * @author piyushbhatia
// */
////public class Weather {
////
////    public static void main(String[] args) {
////        System.out.println("Hello World!");
////    }
////}
//
import javax.swing.*;
import java.awt.*;


public class Weather{
    private static JFrame frame;
    private static JTextField locationFeild;
    private static JTextArea weatherDisplay;
    private static JButton fetchButton;
    
    private static final String apiKey = "c71a3c0964298436fd10d0c75736dff8"; 
    
    
    private static String fetchWeatherData(String city){
        try{
//           // net package 
//                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ city + "&appid=" +apiKey );
//            HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            
//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//      
//            String response ="";
//            String line ;
//            
//            while( (line =reader.readLine())!=null){
//                response+=line;
//            }
//            
//            JSONObject jsonObject = (JSONObject) JSONValue.parse(response);      
//             JSONObject mainobj = (JSONObject) jsonObject.getJSONObject("main");
//            
//            
//            double tempinKelvin = (double)mainobj.get("temp");
//           
//            long humdity = (long)mainobj.get("humidity");
//            
//            double tempCelcius = tempinKelvin - 273.15;
//            System.out.println(tempCelcius);
//            // reterive weather info ..
//            
//            JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
//            JSONObject weather = (JSONObject) weatherArray.get(0);
//            
//            String descp = (String) weather.getString("description");
//             System.out.println(descp);
//            
//            return "Description: "+ descp + "\nTemperature: "+ tempCelcius +"Celicus\nHumidity: " + humdity + "%" ;


      if(city.isEmpty()){
           return "Please enter city .";
        }
  
  

        RestAssured.baseURI= "http://api.openweathermap.org/";
       String response = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8").get("data/2.5/weather")
                .then().log().all()
                .assertThat().statusCode(200).extract().response().asString();

        System.out.println(response);

        JsonPath jsonresponse = new JsonPath(response);
        Float temp  = jsonresponse.get("main.temp");
        Float tempinCelcius = (float) (temp - 273.15);
        System.out.println(tempinCelcius);
        int humidity  = jsonresponse.get("main.humidity");
        System.out.println(humidity);
        String descp = jsonresponse.get("weather[0].description");
        System.out.println(descp);
        
        
        
        //String cityJson =jsonresponse.get("message");
          int responseCode = jsonresponse.get("cod");
            System.out.println("responseCode");
        
        //int windSpeed = jsonresponse.get("wind.speed");
//            System.out.println("windSpeed");
         
        return "Description : "+ descp + "\nTemperature : "+ tempinCelcius + " Celsius\nHumidity : "+ humidity + "%"   ;
    
        }catch(Exception e){
            return "Failed to fetch data";
        }
        
        
        //if RC is 200
        //if city matches - return data
        //else
        //if invalid data - return invalid city message
        //if blank  - throw mess
        //
        
    }
    
    
//    public static Response InvalidCity(){
//        
//        RestAssured.baseURI= "http://api.openweathermap.org/";
//       Response response = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8").get("data/2.5/weather")
//                .then().log().all()
//                .assertThat().statusCode(200).extract().response().asString();
//
//        System.out.println(response);
//        
//        return response ;
//        
//    }
     
    public static void main(String[] args){
         frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,300);
        frame.setLayout(new FlowLayout());
        
         locationFeild = new JTextField(15);
         fetchButton = new JButton("Fetch weather");
         weatherDisplay = new JTextArea(12,30);
        weatherDisplay.setEditable(false);
        
        // display everything 
        frame.add(new JLabel("Enter City Name"));
        frame.add(locationFeild);
        frame.add(fetchButton);
        frame.add(weatherDisplay);
        
        
        fetchButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                String city = locationFeild.getText();
                String weatherInfo = fetchWeatherData(city);
                weatherDisplay.setText(weatherInfo);
                
                
            }
        });
        
        
        frame.setVisible(true);
        
    }
}
