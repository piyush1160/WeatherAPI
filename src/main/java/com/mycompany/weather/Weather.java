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
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Weather{
    private static JFrame frame;
    private static JTextField locationFeild;
    private static JTextArea weatherDisplay;
    private static JButton fetchButton;
    
    private static final String apiKey = "c71a3c0964298436fd10d0c75736dff8"; 
    
    
    private static String fetchWeatherData2(String city){
        try{
          RestAssured.baseURI= "http://api.openweathermap.org/";
        String response2 = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8")
               .get("data/2.5/weather")
                .then().log().all()
                .assertThat().statusCode(404).extract().response().asString();
        }catch(Exception e){
            System.out.println(e);
        }
        return "";
    }
    
    
    private static String fetchWeatherData(String city){
        try{
//           

//      if(city.isEmpty()){
//           return "Please enter city .";
//        }
   if(city.isEmpty()){
        // Show a popup message
        JOptionPane.showMessageDialog(null, "Please enter city.", "Error", JOptionPane.ERROR_MESSAGE);
        return " ";
    }
   
    RestAssured.baseURI= "http://api.openweathermap.org/";
        String response1 = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8")
               .get("data/2.5/weather")
                .then().log().all()
                .assertThat().extract().response().asString();

//        RestAssured.baseURI= "http://api.openweathermap.org/";
//        String response2 = given().queryParam("q",city).queryParam("appid","c71a3c0964298436fd10d0c75736dff8")
//               .get("data/2.5/weather")
//                .then().log().all()
//                .assertThat().statusCode(404).extract().response().asString();

        System.out.println(response1);

        JsonPath jsonresponse = new JsonPath(response1);
        Float temp  = jsonresponse.get("main.temp");
        Float tempinCelcius = (float) (temp - 273.15);
        System.out.println(tempinCelcius);
        int humidity  = jsonresponse.get("main.humidity");
        System.out.println(humidity);
        String descp = jsonresponse.get("weather[0].description");
        System.out.println(descp);
        
        
        

  // String cityJson =jsonresponse.get("message");
  //          int responseCode = jsonresponse.get("sys.cod");
  //            System.out.println("responseCode");
  //            
  //            if(responseCode == 404){
  //                System.out.println("addd valid city");
  //            }
        
        //int windSpeed = jsonresponse.get("wind.speed");
//            System.out.println("windSpeed");
         
        return "Description : "+ descp + "\nTemperature : "+ tempinCelcius + " Celsius\nHumidity : "+ humidity + "%"   ;
    
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Please enter valid city.", "Error", JOptionPane.ERROR_MESSAGE);
            return "";
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
         frame = new JFrame("KeyForecast");
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
       
        //frame.setBackground(new java.awt.Color(230, 230, 250));
        frame.getContentPane().setBackground(new java.awt.Color(230, 230, 250));
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
