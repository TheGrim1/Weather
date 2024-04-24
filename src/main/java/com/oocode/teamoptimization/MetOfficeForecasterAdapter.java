package com.oocode.teamoptimization;

import java.io.IOException;
import java.time.DayOfWeek;

public class MetOfficeForecasterAdapter implements Forecaster {

    public Forecaster.Forecast forecastFor(String place, DayOfWeek dayOfWeek) {


        var dayNumber = dayOfWeek.getValue();
        MetOfficeForecasterClient.Forecast metForecast ;

        MetOfficeWeatherForecasterClient myMetForecaster = new MetOfficeWeatherForecasterClient();
        try {
            metForecast = myMetForecaster.getForecast(place, dayNumber);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Forecaster.Forecast(metForecast.minTemp, metForecast.maxTemp, metForecast.description );
    };

}
