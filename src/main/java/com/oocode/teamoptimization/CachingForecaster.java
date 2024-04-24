package com.oocode.teamoptimization;

import java.time.DayOfWeek;

public class CachingForecaster implements Forecaster{
    private Forecaster delegate;
    public CachingForecaster(Forecaster delegate){
        this.delegate = delegate;
    }
    public Forecaster.Forecast forecastFor(String place, DayOfWeek dayOfWeek){
        return delegate.forecastFor(place, dayOfWeek);
    };
}
