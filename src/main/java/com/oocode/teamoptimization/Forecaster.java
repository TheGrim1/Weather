package com.oocode.teamoptimization;


import java.time.DayOfWeek;

public interface Forecaster {
    public record Forecast(int tempMax, int tempMin, DayOfWeek dayOfWeek){
    }
    public Forecast forecastFor(String place, DayOfWeek dayOfWeek);

}
