
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
        Forecast expectedForecast = new Forecast(1, 2, FRIDAY);
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);

        verify(delegate).forecastFor("Oxford", FRIDAY); // This is arguable
        assertThat(forecast, equalTo(expectedForecast));
    }

    @Test
    public void delegatesForecastingCachingRepeatedForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, FRIDAY);
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecast));

        Forecast expectedForecast2 = new Forecast(2, 3, FRIDAY);
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast2);


        var forecast2 = underTest.forecastFor("Oxford", FRIDAY);

        assertThat(forecast2, equalTo(expectedForecast));

    }

    @Test
    public void delegatesForecastingCachingSeveralDaysForecast() {
        var delegate = mock(Forecaster.class);
        Forecast expectedForecast = new Forecast(1, 2, FRIDAY);
        given(delegate.forecastFor("Oxford", FRIDAY)).willReturn(expectedForecast);

        var underTest = new CachingForecaster(delegate);

        var forecast = underTest.forecastFor("Oxford", FRIDAY);
        assertThat(forecast, equalTo(expectedForecast));

        Forecast expectedForecast2 = new Forecast(2, 3, MONDAY);
        given(delegate.forecastFor("Oxford", MONDAY)).willReturn(expectedForecast2);

        var forecast2 = underTest.forecastFor("Oxford", MONDAY);

        assertThat(forecast2, equalTo(expectedForecast2));
        }
    }
