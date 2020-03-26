package group.csed.frontend.predictions;

import java.util.Calendar;
import java.util.Date;

public class PeriodPrediction {

    private static final int[] monthLens = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private static Date addDays(Date previousDate, int daysToAdd) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(previousDate);
        int monthLen = monthLens[cal.get(Calendar.MONTH)];
        if(cal.get(Calendar.DAY_OF_MONTH) + daysToAdd > monthLen){
            daysToAdd = cal.get(Calendar.DAY_OF_MONTH) + daysToAdd - monthLen - 1;
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
            cal.set(Calendar.DAY_OF_MONTH, 1);
        }
        cal.add(Calendar.DATE, daysToAdd);
        return cal.getTime();
    }

    public static Date getNextPeriodDate(Date previousPeriod, int cycleLen) {
        return addDays(previousPeriod, cycleLen);
    }

    public static double getLengthOfCycle(Date startDate, Date endDate) {
        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        long timeDiff = endTime - startTime;
        return timeDiff / (8.64 * Math.pow(10, 7));
    }
}