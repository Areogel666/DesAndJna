package xxx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.pqsoft.skyeye.util.UTIL;

public class Token {
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	private final static SimpleDateFormat formatss = new SimpleDateFormat("ss");

	public static Object[] exec(String key, int wc, Date date) {
		List<String> str = new ArrayList<String>();
		wc = Math.abs(wc);
		if (wc == 0) {
			String src = key + (Long.parseLong(format.format(date)));
			if (Integer.parseInt(formatss.format(date)) > 30) src += "a";
			else src += "b";
			String m = new MD5().digest(src);
			String s1 = "";
			for (int i = 0; i < 8; i++) {
				Integer iii = 0;
				for (char c : m.substring(4 * i, 4 * (i + 1)).toCharArray()) {
					iii = iii + c;
				}
				s1 += (iii.toString().substring(iii.toString().length() - 1));
			}
			str.add(s1);
		} else {
			for (int i = -wc; i <= wc; i++) {
				for (int j = 0; j < 2; j++) {
					String src = key + (Long.parseLong(format.format(date)) + i);
					src += (j == 0 ? "a" : "b");
					String m = new MD5().digest(src);
					String s1 = "";
					for (int ci = 0; ci < 8; ci++) {
						Integer iii = 0;
						for (char c : m.substring(4 * ci, 4 * (ci + 1)).toCharArray()) {
							iii = iii + c;
						}
						s1 += (iii.toString().substring(iii.toString().length() - 1));
					}
					str.add(s1);
				}
			}
		}
		return str.toArray();
	}

	public static String exec(String key, Date date) {
		return (String) exec(key, 0, date)[0];
	}

	public static void main(String[] args) throws InterruptedException {
		String uuid = "354699045868883";
		UUID.randomUUID().toString();
		while (true) {
			Date date = new Date();
			System.out.println(uuid + "|" + date);
			System.out.println(UTIL.FORMAT.date(date, "yyyy-MM-dd HH:mm:ss") + "|" + Token.exec(uuid, date));
			System.out.print(UTIL.FORMAT.date(date, "yyyy-MM-dd HH:mm:ss") + "|");
			for (Object string : Token.exec(uuid, 1, date)) {
				System.out.print("|" + string);
				if (Token.exec(uuid, date).equals(string)) System.out.print("<true>");
			}
			System.out.println();
			Thread.sleep(5000);
		}
	}
}
