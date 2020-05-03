package group.csed.api.predictions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PeriodPrediction {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
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

    public static String getNextPeriodDate(Date previousPeriod, int cycleLen) {
        Date next = addDays(previousPeriod, cycleLen);
        final long currentTime = System.currentTimeMillis();
        if(currentTime > next.getTime()) {
            int scalar = 1;
            int daysDiff = (int) ((currentTime - next.getTime()) / (1000 * 60 * 60 * 24));
            if(daysDiff > cycleLen) {
                scalar = (daysDiff / cycleLen) + 1;
            }
            return dateFormat.format(addDays(previousPeriod, cycleLen * scalar + cycleLen));
        }
        return dateFormat.format(next);
    }
}