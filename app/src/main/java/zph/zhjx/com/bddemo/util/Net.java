package zph.zhjx.com.bddemo.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.URLEncoder;

/**
 * @author adminZPH
 * */

public class Net {

	public static boolean isNetworkAvailable(Activity activity) {
		Context context = activity.getApplicationContext();
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {

					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static String encodeArgumentAndPath(String urlPart)
			throws Exception {
		String splitRegex = "[/&=? ]";
		String[] split = urlPart.split(splitRegex);
		String newUrlPart = new String(urlPart);

		for (String part : split) {
			newUrlPart = newUrlPart.replace(part,
					URLEncoder.encode(part, "UTF-8"));
		}

		newUrlPart = newUrlPart.replaceAll(" ", "%20");
		return newUrlPart;
	}

	public static String encodeUTF8(String string) {
		String[] split = string.split("^(https?://)?[^/]+(:\\d+)?/");
		String ret = null;
		if (split != null && split.length > 0) {
			int cutLen = string.length() - split[1].length();
			try {
				ret = string.substring(0, cutLen)
						+ encodeArgumentAndPath(split[1]);
			} catch (Exception e) {
				e.printStackTrace();
				ret = string;
			}
		} else {

		}
		return ret;
	}
}
