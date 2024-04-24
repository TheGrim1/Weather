package com.oocode.teamoptimization;


import java.time.DayOfWeek;

public interface Forecaster {
    public record Forecast(int tempMax, int tempMin, String description){
    }
    public Forecast forecastFor(String place, DayOfWeek dayOfWeek);

}
