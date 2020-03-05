package group.csed.api.algorithms;

import java.util.Calendar;
import java.util.Date;

public class LastingCycleAlg {
	public static double getLengthOfCycle(Date startDate, Date endDate) {
		long sDateMili = startDate.getTime();
		long eDateMili = endDate.getTime();
		long miliDateDiff = eDateMili - sDateMili;
		return miliDateDiff / (8.64 * Math.pow(10, 7));
	}
}
