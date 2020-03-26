package group.csed.frontend.predictions;

import java.util.Calendar;
import java.util.Date;

public class PeriodPrediction {

    public static double getLengthOfCycle(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long timeDiff = endTime - startTime;
        return timeDiff / (8.64 * Math.pow(10, 7));
    }

    public static Date getNextPeriodDate(Date previousPeriod, int cycleLen) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(previousPeriod);
        cal.add(Calendar.DATE, cycleLen);
        return cal.getTime();
    }
}