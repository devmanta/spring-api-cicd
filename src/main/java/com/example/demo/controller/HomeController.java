package com.example.demo.controller;

import com.example.demo.service.RetroTestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manta.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HomeController {

    @Value("${greeting.message}")
    private String hi;

    private final TestService testService;

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> testController() {
        Map<String, Object> hm = new HashMap<>();
        hm.put("name", "heejin");
        hm.put("age", 20);
        hm.put("message", hi);
        hm.put("manta",  testService.getManta());

        return ResponseEntity.ok(hm);
    }

    @GetMapping("/api")
    public ResponseEntity<Map<String, Object>> apiTest() {
        try {
            // Specify the REST API URL
            String apiUrl = "https://pokeapi.co/api/v2/pokemon/ditto";

            // Create a URL object from the API URL string
            URL url = new URL(apiUrl);

            // Open a connection to the REST API
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set the HTTP method (e.g., GET, POST, PUT, DELETE)
            connection.setRequestMethod("GET");

            // Send the request and get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the REST API
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response from the REST API
//            System.out.println("Response: " + response.toString());

            // Close the connection
            connection.disconnect();

            String json = response.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap hashMap = objectMapper.readValue(json, HashMap.class);


            List<HashMap> list = (List<HashMap>) hashMap.get("forms");
            HashMap hashMap1 = list.get(0);
            System.out.println("hashMap1 = " + hashMap1);

            return ResponseEntity.ok(hashMap);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @GetMapping("/api2")
    public HashMap apiRetrofitTest() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co").build();

        RetroTestService retroTestService = retrofit.create(RetroTestService.class);
        Call<HashMap> hashMapCall = retroTestService.testRes();
        Response<HashMap> execute = hashMapCall.execute();
        HashMap body = execute.body();

        return body;
    }
}
