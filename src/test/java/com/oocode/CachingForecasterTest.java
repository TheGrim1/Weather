
package com.oocode;

import com.oocode.teamoptimization.CachingForecaster;
import com.oocode.teamoptimization.Forecaster;
import com.oocode.teamoptimization.Forecaster.Forecast;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CachingForecasterTest {
    @Test
    public void delegatesForecastingForNewForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "cold");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);

        verify(delegate).forecastFor("Oxford", FRIDAY); // This is arguable
        assertThat(forecast, equalTo(expectedForecast));
    }

    @Test
    public void delegatesForecastingCachingRepeatedForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "cold");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecast));

        Forecast expectedForecast2 = new Forecast(2, 3, "hot");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast2);


        var forecast2 = underTest.forecastFor("Oxford", FRIDAY);

        assertThat(forecast2, equalTo(expectedForecast));

    }

    @Test
    public void delegatesForecastingCachingSeveralDaysForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, "sunny");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecast));

        Forecast expectedForecast2 = new Forecast(2, 3, "dry");
        given(delegate.forecastFor("Oxford", MONDAY)).willReturn(expectedForecast2);

        var forecast2 = underTest.forecastFor("Oxford", MONDAY);

        assertThat(forecast2, equalTo(expectedForecast2));
        }

    @Test
    public void delegatesForecastingCachingSeveralPlacesForecast() {
        var delegate = mock(Forecaster.class);
        var underTest = new CachingForecaster(delegate);
        Forecast expectedForecastOx = new Forecast(1, 2, "sunny");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecastOx);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecastOx));

        Forecast expectedForecastLon = new Forecast(2, 3, "dry");
        given(delegate.forecastFor("London", FRIDAY)).willReturn(expectedForecastLon);

        var forecast2 = underTest.forecastFor("London", FRIDAY);

        assertThat(forecast2, equalTo(expectedForecastLon));
    }

    @Test
    public void delegatesForecastingCachingClearingTest() {
        var delegate = mock(Forecaster.class);
        var underTest = new CachingForecaster(delegate);
        // create a cache
        Forecast expectedForecastOx = new Forecast(1, 2, "expectedForecastOx");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecastOx);
        // verify that cache
        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecastOx));

        // cache1
        given(delegate.forecastFor("Oxford1", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford1", FRIDAY);

        // cache2
        given(delegate.forecastFor("Oxford2", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford2", FRIDAY);

        // cache3
        given(delegate.forecastFor("Oxford3", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford3", FRIDAY);

        // cache4
        given(delegate.forecastFor("Oxford4", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford4", FRIDAY);

        // cache5
        given(delegate.forecastFor("Oxford5", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford5", FRIDAY);

        // not yet cleared cache -> return old forecast
        Forecast expectedForecast2 = new Forecast(2, 3, "expectedForecast2");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast2);
        var forecast2 = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast2, equalTo(expectedForecastOx));

        // cache6
        given(delegate.forecastFor("Oxford6", FRIDAY)).willReturn(expectedForecastOx);
        underTest.forecastFor("Oxford6", FRIDAY);


        // cleared cache -> return new forecast
        Forecast expectedForecast3 = new Forecast(2, 5, "expectedForecast3");
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast3);
        var forecast3 = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast3, equalTo(expectedForecast3));

    }

    }
