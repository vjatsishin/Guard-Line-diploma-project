package com.example.myapplication;

import com.example.myapplication.dto.ElectricalDevice;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;


public class ConsumptionCalculator {
    private JSONObject jsonArray;
    private Gson gson = new Gson();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    public ConsumptionCalculator(JSONObject jsonArray) {
        this.jsonArray = jsonArray;
        System.out.println(jsonArray.toString());
    }


    public String calculate() {
        try {
            String months = jsonArray.get("months").toString();
            String startMonth = months.substring(0, months.indexOf("-"));
            String endMonth = months.substring(months.indexOf("-") +1);
            System.out.println("startMonth: " + startMonth);
            System.out.println("endMonth: " + endMonth);

            if ((startMonth.charAt(0) == endMonth.charAt(0)) && (startMonth.charAt(1) == endMonth.charAt(1))) {
                System.out.println("we need to calculate for 3rd month");
                jsonArray.put("months", startMonth.charAt(1));
                System.out.println(jsonArray.toString());

                // make an http request to server
            } else {
                System.out.println("we need to calculate for more than 1 month");
                jsonArray.put("months", startMonth.charAt(1) + "-" + endMonth.charAt(1));
                System.out.println(jsonArray.toString());
            }

            ElectricalDevice electricalDevice = gson.fromJson(String.valueOf(jsonArray), ElectricalDevice.class);
            System.out.println(gson.toJson(electricalDevice));


            // make an http request to server

            OkHttpClient ohc = new OkHttpClient();
            String url = "http://10.0.2.2:8181/getCalculation";

            RequestBody requestBody = RequestBody.create(JSON, gson.toJson(electricalDevice));

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "application/json")
//                    .addHeader("Accept", "application/json")
                    .post(requestBody)
                    .build();

            System.out.println("make http request with requestBody: " + requestBody.contentType() + "\n" + request.body().toString());
            Response response = ohc.newCall(request).execute();
            JSONObject responseObj = new JSONObject(response.body().string());

            if (response.code() == 200) {
                return responseObj.get("rs").toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.out.println(e.getLocalizedMessage());
        }
        return "something went wrong";
    }
}
