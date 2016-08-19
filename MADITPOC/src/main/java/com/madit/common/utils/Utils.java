package com.madit.common.utils;


import java.rmi.dgc.VMID;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Venkat
 * Date: 8/17/2016 12:15:38 AM
 *
 */
public final class Utils {

	private Utils() {
	}

	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static int min(int value1, int value2) {
		return value1 >= value2 ? value2 : value1;
	}

	public static int max(int value1, int value2) {
		return value1 <= value2 ? value2 : value1;
	}

	public static int parseInt(String value) {
		int nRet = 0;
		String temp = null;
		if (isEmpty(value))
			return nRet;
		temp = value.trim();
		try {
			nRet = Integer.parseInt(temp);
		} catch (NumberFormatException nEx) {
			log.error((new StringBuilder(
					"Could not convert String to Integer : ")).append(value)
					.toString());
		}
		return nRet;
	}

	public static String getStringFromBundle(ResourceBundle res, String key,
			String defaultValue) {
		String value = getStringFromBundle(res, key);
		if (isEmpty(value))
			value = defaultValue;
		return value;
	}

	public static String getStringFromBundle(ResourceBundle res, String key) {
		String value = "";
		try {
			value = res.getString(key).trim();
		} catch (Exception e) {
			log.error((new StringBuilder("Exception reading resource: "))
					.append(key).append(" from bundle : ")
					.append(res.toString()).toString());
		}
		return value;
	}

	public static int getIntegerFromBundle(ResourceBundle res, String key,
			int defaultValue) {
		String s = getStringFromBundle(res, key);
		if (isEmpty(s)) {
			return defaultValue;
		} else {
			int value = Integer.parseInt(s);
			return value;
		}
	}

	public static String getCurrentSqlTimeStamp() {
		String strRet = "";
		Date dt = new Date();
		long time = dt.getTime();
		Timestamp timeStamp = new Timestamp(time);
		strRet = timeStamp.toString();
		return formatSqlTimeStamp(strRet);
	}

	public static String formatSqlTimeStamp(String string) {
		if (!isEmpty(string)) {
			string = string.replace(' ', '-');
			string = string.replace(':', '.');
		}
		return string;
	}

	public static String replaceParameters(String message, String param1) {
		String s = message;
		s = replaceStringWithAnother(s, "%1", param1);
		return s;
	}

	public static String replaceParameters(String message, String param1,
			String param2) {
		String s = message;
		s = replaceStringWithAnother(s, "%1", param1);
		s = replaceStringWithAnother(s, "%2", param2);
		return s;
	}

	public static String replaceParameters(String message, String param1,
			String param2, String param3) {
		String s = message;
		s = replaceStringWithAnother(s, "%1", param1);
		s = replaceStringWithAnother(s, "%2", param2);
		s = replaceStringWithAnother(s, "%3", param3);
		return s;
	}

	public static String replaceStringWithAnother(String message,
			String sourceString, String targetString) {
		if (isEmpty(message))
			return message;
		int i = message.indexOf(sourceString);
		if (i == -1)
			return message;
		if (i == 0)
			return (new StringBuilder(String.valueOf(targetString))).append(
					message.substring(sourceString.length())).toString();
		else
			return (new StringBuilder(String.valueOf(message.substring(0, i))))
					.append(targetString)
					.append(message.substring(i + sourceString.length()))
					.toString();
	}

	public static String formatSSN(String ssn) {
		for (; ssn.indexOf('-') != -1; ssn = replaceStringWithAnother(ssn, "-",
				""))
			;
		return ssn;
	}

	public static String getValidString(String string) {
		if (isNotEmpty(string))
			return string.trim();
		else
			return "";
	}

	public static String getValidString(Object object) {
		if (object != null)
			return object.toString();
		else
			return "";
	}

	public static String getValidString(double dble) {
		if (dble != 0.0D)
			return (new StringBuilder()).append(dble).toString();
		else
			return "";
	}

	public static boolean getCheckBoxValue(String string) {
		return "on".equalsIgnoreCase(string);
	}

	public static String secondsToString(long time) {
		int seconds = (int) ((time / 1000L) % 60L);
		int minutes = (int) ((time / 60000L) % 60L);
		int hours = (int) ((time / 0x36ee80L) % 24L);
		String secondsStr = (new StringBuilder(
				String.valueOf(seconds >= 10 ? "" : "0"))).append(seconds)
				.toString();
		String minutesStr = (new StringBuilder(
				String.valueOf(minutes >= 10 ? "" : "0"))).append(minutes)
				.toString();
		String hoursStr = (new StringBuilder(String.valueOf(hours >= 10 ? ""
				: "0"))).append(hours).toString();
		return (new StringBuilder(String.valueOf(hoursStr))).append(":")
				.append(minutesStr).append(":").append(secondsStr).toString();
	}

	public static String returnGivenStringOnStatus(String str1, String str2,
			String status) {
		if (isEmpty(status))
			return getValidString(str1);
		else
			return getValidString(str2);
	}

	public static String appendWithComma(String strFinal, String strCurrent) {
		if (isEmpty(strFinal))
			return strCurrent;
		else
			return (new StringBuilder(String.valueOf(strFinal))).append(", ")
					.append(strCurrent).toString();
	}

	public static boolean checkDate(String inDate) {
		try {
			DateFormat format = DateFormat.getDateInstance(3);
			format.setLenient(false);
			format.parse(inDate);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static double parseDouble(String value) {
		double ret = 0.0D;
		String temp = null;
		if (isEmpty(value))
			return ret;
		temp = value.trim();
		try {
			ret = (new Double(temp)).doubleValue();
		} catch (NumberFormatException nEx) {
			return -999D;
		}
		return ret;
	}

	public static Vector getListFromString(String string, String sep) {
		Vector list = new Vector(1);
		String strList;
		for (StringTokenizer toks = new StringTokenizer(string, sep); toks
				.hasMoreTokens(); list.add(strList))
			strList = toks.nextToken();

		return list;
	}

	public static String getStringForList(ArrayList list, String seperator) {
		if (list == null || list.size() <= 0)
			return "";
		StringBuffer resultString = new StringBuffer("");
		int dSize = list.size();
		for (int dCount = 0; dCount < dSize; dCount++) {
			String value = (String) list.get(dCount);
			if (dCount != 0)
				resultString.append(seperator);
			resultString.append(value);
		}

		return resultString.toString();
	}

	public static boolean isAlphaNumeric(String str) {
		boolean result = true;
		if (isNotEmpty(str)) {
			char chars[] = str.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (Character.isLetterOrDigit(chars[i]))
					continue;
				result = false;
				break;
			}

		}
		return result;
	}

	public static boolean isNumeric(String str) {
		if (isNotEmpty(str) && !"0".equals(str)) {
			str = str.trim();
			int intValue = parseInt(str);
			return intValue != 0;
		}
		return "0".equals(str);
	}

	public static String formatZip(String zip) {
		if (zip.length() > 5) {
			String temp = zip.substring(5);
			zip = zip.substring(0, 5).concat("-").concat(temp);
		}
		return zip;
	}

	public static String formatDate(String date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.s");
		sdf1.setLenient(false);
		Date parsedDt;
		try {
			parsedDt = sdf1.parse(date);
		} catch (Exception e) {
			return date;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		date = sdf.format(parsedDt);
		return date;
	}

	public static String formatDate(String date, String fromFormat,
			String toFormat) {
		if (date == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(fromFormat);
		sdf1.setLenient(false);
		Date parsedDt;
		try {
			parsedDt = sdf1.parse(date);
		} catch (Exception e) {
			return date;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(toFormat);
		date = sdf.format(parsedDt);
		return date;
	}

	public static String formatDate(String date, String fromFormat,
			String toFormat, DateFormatSymbols dateFormatSymbols) {
		if (date == null)
			return "";
		SimpleDateFormat sdf1 = new SimpleDateFormat(fromFormat,
				dateFormatSymbols);
		sdf1.setLenient(false);
		Date parsedDt;
		try {
			parsedDt = sdf1.parse(date);
		} catch (Exception e) {
			log.error("Error Occured in Format Date method::", e);
			return date;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(toFormat, dateFormatSymbols);
		date = sdf.format(parsedDt);
		return date;
	}

	/*
	 * public static String formatDate(String date, String fromFormat, String
	 * toFormat, Locale locale) { if (isEmpty(date)) { return ""; } else {
	 * DateFormatSymbols dateFormatSymbols = getDateFormatSymbols(locale);
	 * return formatDate(date, fromFormat, toFormat, dateFormatSymbols); } }
	 * 
	 * public static byte[] encodeBase64(String str) { if (str == null) return
	 * null; else return Base64.encode(str.getBytes()).getBytes(); }
	 * 
	 * public static String decodeBase64(byte byteArray[]) { if (byteArray ==
	 * null) return null; else return new String(Base64.decode(new
	 * String(byteArray))); }
	 * 
	 * public static byte[] encodeBase64(byte byteArray[]) { if (byteArray ==
	 * null) return null; else return Base64.encode(byteArray).getBytes(); }
	 * 
	 * public static byte[] decodeBase64ReturnByteArray(byte byteArray[]) { if
	 * (byteArray == null) return null; else return Base64.decode(new
	 * String(byteArray)); }
	 */

	public static Vector convertArrayToVector(Object obj[]) {
		return new Vector(Arrays.asList(obj));
	}

	public static boolean isEmpty(Object objArr[]) {
		return objArr == null || objArr.length < 1;
	}

	public static boolean isNotEmpty(Object objArr[]) {
		return !isEmpty(objArr);
	}

	public static boolean isEmpty(List listObj) {
		return listObj == null || listObj.size() == 0;
	}

	public static boolean isNotEmpty(List listObj) {
		return !isEmpty(listObj);
	}

	public static boolean isEmpty(Map map) {
		return map == null || map.isEmpty();
	}

	public static boolean isNotEmpty(Map map) {
		return !isEmpty(map);
	}

	public static boolean checkDate(String inDate, String dateFormatStr) {
		try {
			SimpleDateFormat dateFormatObj = null;
			if (isNotEmpty(dateFormatStr))
				dateFormatObj = new SimpleDateFormat(dateFormatStr);
			else
				dateFormatObj = new SimpleDateFormat("MM/dd/yyyy");
			dateFormatObj.setLenient(false);
			dateFormatObj.parse(inDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	/*
	 * public static GYearMonth convertMMYYYYToGYearMonth(String strDate) { Date
	 * parsedDt = null; GYearMonth gyrMnth = null; try { SimpleDateFormat sdf =
	 * new SimpleDateFormat("MM/yyyy"); parsedDt = sdf.parse(strDate); gyrMnth =
	 * CastorDateHelper .convertSQLDateToCastorGYearMonth(parsedDt); } catch
	 * (ParseException e) { log.error(e.getMessage(), e); } return gyrMnth; }
	 * 
	 * public static String convertGYearMonthToMMYYYY(GYearMonth gyrMnth) {
	 * String MMYYYYDt = null; try { MMYYYYDt = formatDate(gyrMnth.toString(),
	 * "yyyy-MM", "MM/yyyy"); } catch (Exception e) { log.error(e.getMessage(),
	 * e); } return MMYYYYDt; }
	 */

	public static String generateTransactionId(String inPrefix) {
		VMID vmid = new VMID();
		return (new StringBuilder(String.valueOf(inPrefix))).append(
				vmid.hashCode()).toString();
	}

	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}

	public static final double roundDouble(double d, int places) {
		return (double) Math.round(d * Math.pow(10D, places))
				/ Math.pow(10D, places);
	}

	public static Date formatDate(String strDate, String format) {
		Date date = null;
		SimpleDateFormat parser = new SimpleDateFormat(format);
		try {
			date = parser.parse(strDate);
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		return date;
	}

	public static int daysBetweenDates(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.set(date1.getYear(), date1.getMonth(), date1.getDate());
		calendar2.set(date2.getYear(), date2.getMonth(), date2.getDate());
		long milliseconds1 = calendar1.getTimeInMillis();
		long milliseconds2 = calendar2.getTimeInMillis();
		long diff = milliseconds2 - milliseconds1;
		int diffDays = (int) (diff / 0x5265c00L);
		return diffDays + 1;
	}

	/*
	 * public static int daysBetweenDates(org.exolab.castor.types.Date fromDate,
	 * Date toDate) { Date fromJavaDate = new Date(fromDate.getCentury() * 100 +
	 * fromDate.getYear(), fromDate.getMonth(), fromDate.getDay()); Calendar cal
	 * = new GregorianCalendar(); cal.setTime(toDate); int year = cal.get(1);
	 * int month = cal.get(2) + 1; int date = cal.get(5); Date toJavaDate = new
	 * Date(year, month, date); long diff = toJavaDate.getTime() -
	 * fromJavaDate.getTime(); int diffDays = (int) (diff / 0x5265c00L); return
	 * diffDays; }
	 */
	public static int getYearFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			result = cal.get(1);
		}
		return result;
	}

	public static int getMonthFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			result = cal.get(2);
		}
		return result + 1;
	}

	public static int getDateFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			result = cal.get(5);
		}
		return result;
	}

	public static String formatNumber(Number number, Locale locale) {
		if (number == null) {
			log.error(String.format("Input number is null :: %s ",
					new Object[] { number }));
			return null;
		}
		if (locale == null) {
			log.error(String.format("Locale is null :: %s ",
					new Object[] { locale }));
			return null;
		}
		String formattedNumber = null;
		try {
			formattedNumber = NumberFormat.getCurrencyInstance(locale).format(
					number);
		} catch (NumberFormatException nfe) {
			log.error(nfe.getMessage(), nfe);
		}
		return formattedNumber;
	}

	public static String formatNumber(String number, Locale locale) {
		if (isEmpty(number)) {
			log.error(String.format("Input number is null :: %s ",
					new Object[] { number }));
			return null;
		}
		if (locale == null) {
			log.error(String.format("Locale is null :: %s ",
					new Object[] { locale }));
			return null;
		}
		Number inputNumber = null;
		try {
			NumberFormat numberFormat = NumberFormat.getInstance();
			inputNumber = numberFormat.parse(number);
		} catch (ParseException pe) {
			log.error(pe.getMessage(), pe);
		}
		return formatNumber(inputNumber, locale);
	}

	/*
	 * public static DateFormatSymbols getDateFormatSymbols(Locale locale) { if
	 * (locale == null) { log.error(String.format(
	 * "Locale is null, So setting default(en_CA) locale :: %s", new Object[] {
	 * locale })); locale = Locale.CANADA; } DateFormatSymbols dfs =
	 * (DateFormatSymbols) dateFormatSymbolsCacheMap .get(locale); if (dfs !=
	 * null) return dfs; if (StringUtils.equalsIgnoreCase("en_CA",
	 * locale.toString()) || StringUtils.equalsIgnoreCase("en_US",
	 * locale.toString())) { if (log.isDebugEnabled()) log.debug(String .format(
	 * "DateFormatSymbols :: %s not found in dateFormatSymbolsCacheMap for the locale :: %s, so creating new one and adding to cacheMap "
	 * , new Object[] { dfs, locale })); dfs = new DateFormatSymbols(locale);
	 * dateFormatSymbolsCacheMap.put(locale, dfs); return dfs; } if
	 * (StringUtils.equalsIgnoreCase("fr_CA", locale.toString())) { if
	 * (log.isDebugEnabled()) log.debug(String .format(
	 * "DateFormatSymbols :: %s not found in dateFormatSymbolsCacheMap for the locale :: %s, so creating new one and adding to cacheMap "
	 * , new Object[] { dfs, locale })); dfs = new DateFormatSymbols(locale);
	 * dateFormatSymbolsCacheMap.put(locale, dfs); String oldMonths[] =
	 * dfs.getShortMonths(); String newMonths[] = new String[oldMonths.length];
	 * int i = 0; for (int len = oldMonths.length; i < len; i++) { String
	 * oldMonth = oldMonths[i]; if (oldMonth.endsWith(".")) { newMonths[i] =
	 * oldMonth.substring(0, oldMonths[i].length() - 1); if
	 * (!StringUtils.equalsIgnoreCase("juil", newMonths[i]) &&
	 * newMonths[i].length() > 3) newMonths[i] = newMonths[i].substring(0, 3); }
	 * else if (oldMonth.length() > 3) newMonths[i] = oldMonth.substring(0, 3);
	 * else newMonths[i] = oldMonth; }
	 * 
	 * dfs.setShortMonths(newMonths); return dfs; } else { return null; } }
	 */
	public static String formatQuery(String preparedStmtQuery,
			Object arguments[]) {
		if (isEmpty(preparedStmtQuery)) {
			log.error(String.format(
					"Input query string is either null or empty :: %s",
					new Object[] { preparedStmtQuery }));
			return null;
		} else {
			String query = preparedStmtQuery;
			query = query.replaceAll("\\?", "%s");
			return String.format(query, arguments);
		}
	}

	public static String buildQuery(String queryPattern, Object arguments[])
			throws IllegalArgumentException {
		String query = null;
		query = MessageFormat.format(queryPattern, arguments);
		return query;
	}

	public static String maskPassphraseAndPassword(String inputXML)
			throws Exception {
		log.info("Start Executing maskPassphraseAndPassword(String inputXML) method");
		if (isEmpty(inputXML)) {
			log.error(String
					.format("InputXML is either null or empty :: %s, so returning empty string ",
							new Object[] { inputXML }));
			return "";
		}
		String maskedXML = inputXML;
		try {
			if (maskedXML.contains("<Password>")
					|| maskedXML.contains("<Password "))
				maskedXML = maskString(maskedXML, "Password");
			if (maskedXML.contains("<NewPassword>")
					|| maskedXML.contains("<NewPassword "))
				maskedXML = maskString(maskedXML, "NewPassword");
			if (maskedXML.contains("<Passphrase>")
					|| maskedXML.contains("<Passphrase "))
				maskedXML = maskString(maskedXML, "Passphrase");
			if (maskedXML.contains("<NewPassphrase>")
					|| maskedXML.contains("<NewPassphrase "))
				maskedXML = maskString(maskedXML, "NewPassphrase");
		} catch (Exception e) {
			throw e;
		}
		log.info("End Executing maskPassphraseAndPassword(String inputXML) method");
		return maskedXML;
	}

	private static String maskString(String inputXML, String tagName)
			throws Exception {
		log.info("Start Executing maskString( String inputXML, String tagName ) method ");
		if (isEmpty(inputXML)) {
			log.error(String
					.format("InputXML is either null or empty :: %s, so returning empty string in maskString( String inputXML, String tagName ) method",
							new Object[] { inputXML }));
			return "";
		}
		if (isEmpty(tagName)) {
			log.error(String
					.format("TagName is either null or empty :: %s, so returning empty string in maskString( String inputXML, String tagName ) method",
							new Object[] { tagName }));
			return "";
		}
		String temp1 = inputXML;
		try {
			String genString = "";
			String actualString = "";
			temp1 = (new StringBuilder(String.valueOf(inputXML.substring(
					temp1.indexOf((new StringBuilder("<")).append(tagName)
							.append(" xmlns=\"\">").toString()),
					temp1.indexOf((new StringBuilder("</")).append(tagName)
							.append(">").toString()))))).append("</")
					.append(tagName).append(">").toString();
			temp1 = temp1.substring(
					0,
					temp1.indexOf((new StringBuilder("</")).append(tagName)
							.append(">").toString()));
			String split[] = temp1.split((new StringBuilder("<"))
					.append(tagName).append(" xmlns=\"\">").toString());
			for (int i = 0; i < split.length; i++) {
				actualString = split[i];
				for (int j = 0; j < split[i].length(); j++)
					genString = (new StringBuilder(String.valueOf(genString)))
							.append("*").toString();

			}

			temp1 = inputXML.replace(actualString, genString);
		} catch (Exception e) {
			throw new Exception("Exception while executing maskString method");
		}
		log.info("End Executing maskString( String inputXML, String tagName ) method ");
		return temp1;
	}

	public static Timestamp getCurrentGMTTimeInSQLTimeStamp() {
		log.info("Start Executing getCurrentGMTTimeInSQLTimeStamp() method ");
		Calendar cal = Calendar.getInstance();
		Timestamp ts = new Timestamp(cal.getTimeInMillis() - (long) cal.get(15)
				- (long) cal.get(16));
		log.info("End Executing getCurrentGMTTimeInSQLTimeStamp() method ");
		return ts;
	}

	public static Date getCurrentGMTDate() {
		log.info("Start Executing getCurrentGMTDate() method ");
		Timestamp ts = getCurrentGMTTimeInSQLTimeStamp();
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTimeInMillis(ts.getTime());
		log.info("End Executing getCurrentGMTDate() method ");
		return dateCal.getTime();
	}

	public static Date getCurrentDate(String clientTimeZone) {
		TimeZone timeZone = null;
		if (isEmpty(clientTimeZone))
			timeZone = Calendar.getInstance().getTimeZone();
		else
			timeZone = TimeZone.getTimeZone(clientTimeZone);
		return getCurrentDate(timeZone);
	}

	public static Date getCurrentDate(TimeZone clientTimeZone) {
		log.info("Start Executing getCurrentDate( clientTimeZone ) method of Utils ");
		if (clientTimeZone == null) {
			log.info("clientTimeZone is null, so returning the System current date ...");
			return Calendar.getInstance().getTime();
		}
		Calendar localCalendar = Calendar.getInstance();
		long localTime = localCalendar.getTimeInMillis();
		long localOffsetFromGMT = localCalendar.get(15);
		long gmtTime = (new Date(localTime - localOffsetFromGMT)).getTime();
		int clientTimeZoneOffset = clientTimeZone.getOffset(gmtTime);
		Calendar estCalendar = Calendar.getInstance();
		estCalendar.setTime(new Date(gmtTime + (long) clientTimeZoneOffset));
		Date currentDate = estCalendar.getTime();
		if (log.isDebugEnabled())
			log.debug(String.format(
					"Current Date :: %s, for the passed clientTimeZone :: %s",
					new Object[] { currentDate, clientTimeZone.getID() }));
		log.info("End Executing getCurrentDate( clientTimeZone ) method of Utils ");
		return currentDate;
	}

	/*
	 * public static Date getCurrentDateInEST() throws Exception { return
	 * getCalendarInEST().getTime(); }
	 */
	/*
	 * public static Calendar getCalendarInEST() throws Exception {
	 * log.info("Start Executing getCalendarInEST() method "); Calendar
	 * localCalendar = Calendar.getInstance(); Calendar estCalendar =
	 * Calendar.getInstance(); estCalendar.setTime(new
	 * Date(localCalendar.getTime().getTime() + (long)
	 * BCSConstants.EST_TIMEZONE.getRawOffset()));
	 * log.info("End Executing getCalendarInEST() method "); return estCalendar;
	 * }
	 */
	/*
	 * public static String getGMTDate(String date, String dateFormat, Locale
	 * locale) throws Exception { log.info(
	 * "Start Executing getGMTDate( String date, String dateFormat ) method ");
	 * DateFormatSymbols dateFormatSymbols = getDateFormatSymbols(locale);
	 * SimpleDateFormat gmtSDF = new SimpleDateFormat(dateFormat,
	 * dateFormatSymbols); gmtSDF.setTimeZone(BCSConstants.GMT_TIMEZONE);
	 * Calendar cal = Calendar.getInstance(); Calendar estCalendar =
	 * getCalendarInEST(); Date estDate = gmtSDF.parse(date); Calendar cal1 =
	 * Calendar.getInstance(); cal1.setTime(estDate); cal1.set(10,
	 * estCalendar.get(10)); cal1.set(12, estCalendar.get(12)); cal1.set(13,
	 * estCalendar.get(13)); String gmtDate = gmtSDF.format(new
	 * Date(gmtSDF.parse(date).getTime() - (long) cal.get(15) - (long)
	 * cal.get(16))); if (log.isDebugEnabled())
	 * log.debug(String.format("GMT Date :: %s for the given date :: %s", new
	 * Object[] { gmtDate, date }));
	 * log.info("End Executing getGMTDate( String date, String dateFormat ) method "
	 * ); return gmtDate; }
	 */
	/*
	 * public static String getESTDate(String date, String dateFormat, Locale
	 * locale) throws Exception { log.info(
	 * "Start Executing getESTDate( String date, String dateFormat ) method ");
	 * DateFormatSymbols dateFormatSymbols = getDateFormatSymbols(locale);
	 * SimpleDateFormat gmtDFT = new SimpleDateFormat(dateFormat,
	 * dateFormatSymbols); Calendar localCalendar = Calendar.getInstance();
	 * Calendar cal = Calendar.getInstance(); Date da = gmtDFT.parse(date);
	 * cal.setTime(da); cal.set(11, localCalendar.get(11)); cal.set(12,
	 * localCalendar.get(12)); cal.set(13, localCalendar.get(13));
	 * SimpleDateFormat estDFT = new SimpleDateFormat(dateFormat,
	 * dateFormatSymbols); Calendar estCalendar = Calendar.getInstance();
	 * estCalendar.setTime(new Date(cal.getTime().getTime() + (long)
	 * BCSConstants.EST_TIMEZONE.getRawOffset())); String estDate =
	 * estDFT.format(estCalendar.getTime()); if (log.isDebugEnabled())
	 * log.debug(String.format("EST Date :: %s for the given date :: %s", new
	 * Object[] { estDate, date }));
	 * log.info("End Executing getESTDate( String date, String dateFormat ) method "
	 * ); return estDate; }
	 * 
	 * public static String convertESTDateToGMTDate(String inputDate, String
	 * pattern) { SimpleDateFormat gmtSDF; if(isEmpty(inputDate)) {
	 * log.error(String.format("Input is either null or empty :: %s", new
	 * Object[] { inputDate })); return null; } if(isEmpty(pattern)) pattern =
	 * "MM/dd/yyyy"; gmtSDF = new SimpleDateFormat(pattern); String gmtDate;
	 * Calendar estCalendar = getCalendarInEST(); Date estDate1 =
	 * gmtSDF.parse(inputDate); Calendar cal1 = Calendar.getInstance();
	 * cal1.setTime(estDate1); cal1.set(11, estCalendar.get(11)); cal1.set(12,
	 * estCalendar.get(12)); cal1.set(13, estCalendar.get(13)); gmtSDF = new
	 * SimpleDateFormat(pattern); gmtSDF.setTimeZone(BCSConstants.GMT_TIMEZONE);
	 * Calendar c = Calendar.getInstance(); int offSet =
	 * BCSConstants.EST_TIMEZONE.getRawOffset(); int offSet1 =
	 * c.getTimeZone().getOffset(c.getTimeInMillis()); int utc = offSet1 +
	 * offSet; c.setTime(cal1.getTime()); c.set(14, -utc); gmtDate =
	 * gmtSDF.format(c.getTime()); if(log.isDebugEnabled()) {
	 * log.debug(String.format("GMT Time :: %s, EST Time :: %s", new Object[] {
	 * c.getTime(), cal1.getTime() })); log.debug(String.format(
	 * "Converted GMT Date :: %s for the input EST Date :: %s in convertESTDateToGMTDate method"
	 * , new Object[] { gmtDate, inputDate })); } return gmtDate; Exception e;
	 * e; log.error(e.getMessage(), e); return null; }
	 * 
	 * public static String appendTimeToDate( org.exolab.castor.types.Date
	 * castorDate, Time castorTime) { StringBuffer appendedTime = new
	 * StringBuffer(); if (castorDate != null)
	 * appendedTime.append(convertToBeanFormat(castorDate)); if (castorTime !=
	 * null) { Time time = castorTime; appendedTime.append(" "); if
	 * (time.toString().length() > 8)
	 * appendedTime.append(time.toString().substring(0, 8)); else
	 * appendedTime.append(time.toString()); } return appendedTime.toString(); }
	 */
	public static TimeZone getTimeZone(String timeZone) {
		if (isEmpty(timeZone))
			return Calendar.getInstance().getTimeZone();
		else
			return TimeZone.getTimeZone(timeZone);
	}

	/*
	 * public static TimeZone getTimeZone(Time castorTime) {
	 * log.info("Start Executing getTimeZone( Time castorTime ) method of Utils"
	 * ); TimeZone timeZone = null; if (castorTime == null) { timeZone =
	 * Calendar.getInstance().getTimeZone(); return timeZone; } short zoneHour =
	 * castorTime.getZoneHour(); short zoneMin = castorTime.getZoneMinute();
	 * boolean isZoneNegative = castorTime.isZoneNegative(); StringBuffer
	 * zoneOffSet = new StringBuffer("GMT"); if (isZoneNegative)
	 * zoneOffSet.append("-"); else zoneOffSet.append("+"); if (zoneHour < 10) {
	 * zoneOffSet.append("0"); zoneOffSet.append(zoneHour); } else {
	 * zoneOffSet.append(zoneHour); } zoneOffSet.append(":"); if (zoneMin < 10)
	 * { zoneOffSet.append("0"); zoneOffSet.append(zoneMin); } else {
	 * zoneOffSet.append(zoneMin); } timeZone =
	 * TimeZone.getTimeZone(zoneOffSet.toString()); if (log.isDebugEnabled())
	 * log.debug(String.format(
	 * "TimeZone :: %s, for the input castorTime :: %s", new Object[] { timeZone
	 * == null ? null : timeZone.getID(), castorTime }));
	 * log.info("End Executing getTimeZone( Time castorTime ) method of Utils");
	 * return timeZone; }
	 */
	public static int getOffset(String clientTimeZone) {
		if (isEmpty(clientTimeZone)) {
			return 0;
		} else {
			TimeZone timeZone = TimeZone.getTimeZone(clientTimeZone);
			return timeZone.getOffset(Calendar.getInstance().getTimeInMillis());
		}
	}

	public static String toLowerCamelCase(String string) {
		if (string == null) {
			return null;
		} else {
			StringBuffer sb = new StringBuffer(string);
			sb.replace(0, 1, string.substring(0, 1).toLowerCase());
			return sb.toString();
		}
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	private static SimpleDateFormat sdf1 = new SimpleDateFormat(
			"EEE, d MMM yyyy HH:mm:ss Z");
	private static SimpleDateFormat sqldf = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm.ss.SSSS");
	public static final String CASTOR_DF = "yyyy-MM-dd";
	public static final String MMYYYY = "MM/yyyy";
	private static final String JUIL = "juil";
	private static Map dateFormatSymbolsCacheMap = new ConcurrentHashMap(1);

}