package group.csed.api.algorithms;

import java.util.Calendar;
import java.util.Date;

public class NextPrediction {

    public static Date nextPrediction(Date previousPeriod, int cycleLen){
        Calendar cal = Calendar.getInstance();
        cal.setTime(previousPeriod);
        cal.add(Calendar.DATE, cycleLen);
        return cal.getTime();
    }
}
