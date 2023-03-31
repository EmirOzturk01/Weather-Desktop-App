package com.example.aaaaaaaaaaaaa;

import java.io.FileNotFoundException;

public class City {
    private String name;
    private Double temp;
    private Double tempMin;
    private Double feelsLike;
    private Double tempMax;
    private String country;
    private String description;
    private String main;



    public City(String name,Double temp, Double tempMin, Double feelsLike, Double tempMax,String country,String description,String main) throws FileNotFoundException {
        this.name = name;
        this.temp = K_C(temp);
        this.tempMin = K_C(tempMin);
        //this.humidity = (int)humidity;
        this.feelsLike = K_C(feelsLike);
        this.tempMax = K_C(tempMax);
        this.country = country;
        this.description = description;
        this.main=main;

    }

    static double K_C(Object K){
        double C = (Double)K - 273.15;
        return Math.round(C);
    }

    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public Double getTempMax() {
        return tempMax;
    }
}
