package group.csed.test;

import group.csed.api.predictions.PeriodPrediction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodPredictionTest {

    @Test
    public void nextPeriodDate() throws Exception {
        final Date started = new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-29");
        final Date prediction = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
                .parse(PeriodPrediction.getNextPeriodDate(started, 1));
        Assertions.assertThat(prediction.getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    public void nextPeriodDateComplex() throws Exception {
        final Date started = new SimpleDateFormat("yyyy-MM-dd").parse("1980-04-29");
        final Date prediction = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'")
                .parse(PeriodPrediction.getNextPeriodDate(started, 28));
        Assertions.assertThat(prediction.getTime()).isGreaterThan(System.currentTimeMillis());
    }

    @Test
    public void invalidCycleLen() throws Exception {
        final Date started = new SimpleDateFormat("yyyy-MM-dd").parse("1980-04-29");
        final String prediction = PeriodPrediction.getNextPeriodDate(started, -1);
        Assertions.assertThat(prediction).isEqualTo(null);
    }

    @Test
    public void futureStartDate() throws Exception {
        final Date started = new SimpleDateFormat("yyyy-MM-dd").parse("2020-10-10");
        final String prediction = PeriodPrediction.getNextPeriodDate(started, 5);
        Assertions.assertThat(prediction).isNotEqualTo(null);
    }
}