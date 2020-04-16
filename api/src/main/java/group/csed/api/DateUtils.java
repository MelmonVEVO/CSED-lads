package group.csed.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String formatCurrDate() {
        return dateFormat.format(new Date());
    }
}