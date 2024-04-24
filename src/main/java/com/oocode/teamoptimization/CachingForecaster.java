package com.oocode.teamoptimization;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class CachingForecaster implements Forecaster{
    private Forecaster delegate;
    private Forecast lastForecast;

    Map<String, Forecast> cachedVales = new LinkedHashMap<>();

    public CachingForecaster(Forecaster delegate){
        this.delegate = delegate;
    }

    private void clearObsoleteCache(){
        if (cachedVales.size() >1){
            int counter = 0;
            for (String k: cachedVales.keySet()){
                    counter += 1;
                    if (counter >1){
                        cachedVales.remove(k);
                    }
            }

        }
    }

    public Forecaster.Forecast forecastFor(String place, DayOfWeek dayOfWeek){
        clearObsoleteCache();
        var cachingKey = dayOfWeek.toString() + place;
        if (cachedVales.get(cachingKey) == null){
            this.cachedVales.put(cachingKey,delegate.forecastFor(place, dayOfWeek));
        }
        return this.cachedVales.get(cachingKey);
    };
}
