package com.oocode.teamoptimization;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class CachingForecaster implements Forecaster{
    private Forecaster delegate;
    private Forecast lastForecast;

    Map<DayOfWeek, Forecast> cachedVales = new HashMap<>();

    public CachingForecaster(Forecaster delegate){
        this.delegate = delegate;
    }

    public Forecaster.Forecast forecastFor(String place, DayOfWeek dayOfWeek){
        if (cachedVales.get(dayOfWeek) == null){
            this.cachedVales.put(dayOfWeek,delegate.forecastFor(place, dayOfWeek));
        }
        return this.cachedVales.get(dayOfWeek);
    };
}
