package group.csed.api.algorithms;

import java.util.Calendar;
import java.util.Date;

public class NextPrediction {

    static final int[] monthLens = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static Date addDays(Date startingDate, int daysToAdd){
        Calendar cal = Calendar.getInstance();
        cal.setTime(previousPeriod);
        int monthLen = monthLen[cal.get(Calendar.MONTH) - 1];
        if(cal.get(Calendar.DAY_OF_MONTH) + daysToAdd > monthLen){
            int daysToAdd = cal.get(Calendar.DAY_OF_MONTH) + daysToAdd - mnnthLen;
            cal.add(Calendar.MONTH, 1);
        }
        cal.add(Calendar.DATE, daysToAdd);
        return cal.getTime();
    }

    public static Date nextPrediction(Date previousPeriod, int cycleLen){
        return addDays(previousPerid, cycleLen);
    }
}
