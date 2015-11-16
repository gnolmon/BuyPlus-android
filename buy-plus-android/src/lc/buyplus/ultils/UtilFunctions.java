package lc.buyplus.ultils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class UtilFunctions {
	public static boolean dateValidate(String d) {
		String dateArray[] = d.split("-");
		int day = Integer.parseInt(dateArray[0]);
		int month = Integer.parseInt(dateArray[1]);
		int year = Integer.parseInt(dateArray[2]);
		boolean leapYear = false;
		if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
			leapYear = true;
		}
		if (year > 2099 || year < 1900)
			return false;
		if (month < 13) {
			if (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) {
				if (day > 31)
					return false;
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				if (day > 30)
					return false;
			} else if (leapYear == true && month == 2) {
				if (day > 29)
					return false;
			} else if (leapYear == false && month == 2) {
				if (day > 28)
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static String getDayFromDate(String d) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("Mon", "Thứ Hai");
		map.put("Tue", "Thứ Ba");
		map.put("Wed", "Thứ Tư");
		map.put("Thu", "Thứ Năm");
		map.put("Fri", "Thứ Sáu");
		map.put("Sat", "Thứ Bảy");
		map.put("Sun", "Chủ Nhật");
		try {
			boolean dateValid = dateValidate(d);
			if (dateValid == true) {
				SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date date = df.parse(d);
				df.applyPattern("EEE");
				return map.get(df.format(date));
			} else {
				System.out.println("Invalid Date!!!");
			}
		} catch (Exception e) {
			System.out.println("Invalid Date Formats!!!");
		}
		return null;
	}

	public static int getRandom(int x) {
		return (int) (Math.random() * x + 1);
	}

	public static String getFullDate() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getTime() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getTimeHM_FullDate() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public int getScreenWidth(Context context) {
        int columnWidth;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
 
        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) {
            // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}
