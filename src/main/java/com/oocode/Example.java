package com.oocode;

import com.oocode.teamoptimization.CachingForecaster;
import com.oocode.teamoptimization.Forecaster;
import com.oocode.teamoptimization.MetOfficeForecasterAdapter;
import com.oocode.teamoptimization.MetOfficeForecasterClient.Forecast;
import com.oocode.teamoptimization.MetOfficeWeatherForecasterClient;

import java.io.IOException;
import java.time.DayOfWeek;

public class Example {
    public static void main(String[] args) throws IOException {
        Forecaster forecaster = new CachingForecaster(new MetOfficeForecasterAdapter());

        printForecast(forecaster);
        printForecast(forecaster);
        printForecast(forecaster);
        printForecast(forecaster);
        printForecast(forecaster);
    }

    private static void printForecast(Forecaster forecaster) {
        String day = "Wednesday";
        String place = "Oxford";
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
        Forecaster.Forecast forecast = forecaster.forecastFor(place, dayOfWeek);
        System.out.println(forecast.toString());
    }
}
