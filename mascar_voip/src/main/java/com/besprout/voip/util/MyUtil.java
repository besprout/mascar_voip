package com.besprout.voip.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.web.client.RestTemplate;

import com.besprout.voip.bean.Address;

public class MyUtil {
	
	private static RestTemplate template;
	
	private static final Logger logger=Logger.getLogger(MyUtil.class);
	
	public static String getSubString(String value, int length) {
		if (value == null) {
			return "";
		}
		if (value.length() <= length) {
			return value;
		}
		return value.substring(0, length) + "...";
	}
	
	public static List<Address> searchAddress(String address){
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("address", address);
		parameters.put("language", "en-US");
		parameters.put("sensor", "true");
		String reqUrl = "http://maps.google.com/maps/api/geocode/json";
		
//		AddressBookDetail detail = new AddressBookDetail();
//		detail.setDetailAddress("");
//		detail.setLatlng("18.458792,-66.537234");
//		list.add(detail);
		
		String response = sendRequest(reqUrl + "?" + generatorParamString(parameters), "GET", null);
		if (response != null) {
			JsonNode parent = JsonUtils.readJsonEntity(response);
			if(parent != null) {
				if (parent.get("status").asText().equals("OK")) {
					parent = parent.get("results");
					
					List<Address> addressList = new ArrayList<Address>();
					for (int i = 0; i < parent.size(); i++) {
						Address ad = new Address();
						JsonNode addressComponents = parent.get(i).get("address_components");
						for (int j = 0; j < addressComponents.size(); j++) {
							JsonNode node = addressComponents.get(j);
							String longName = node.get("long_name").asText();
							String type = node.get("types").get(0).asText();
							if(type.equals("subpremise")){
								ad.setSubpremise(longName);
								
							}else if(type.equals("street_number")){
								ad.setStreetNumber(longName);
								
							}else if(type.equals("route")){
								ad.setRoute(longName);
								
							}else if(type.equals("neighborhood")){
								ad.setNeighborhood(longName);
								
							}
						}
						
						
						JsonNode location = parent.get(i).get("geometry").get("location");
						ad.setShortAddress(parent.get(i).get("formatted_address").asText());
						ad.setLat(location.get("lat").getDoubleValue());
						ad.setLng(location.get("lng").getDoubleValue());
						addressList.add(ad);
					}
					
					return addressList;
				}
			}
		}
		
		return null;
	}
	
	
	public static String sendRequest(String reqUrl, String requestMethod, Map<String, String> parameters) {
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(5000);//5秒后超时
			conn.setReadTimeout(5000);//5秒后超时
			if (requestMethod.equals("POST")) {
				String params = generatorParamString(parameters);
				byte[] bytes = params.getBytes();
				OutputStream out = conn.getOutputStream();
				out.write(bytes, 0, bytes.length);
				out.flush();
				out.close();
			}
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String readLine;
			StringBuffer sb = new StringBuffer();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			br.close();
			isr.close();
			is.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String generatorParamString(Map<String, String> parameters) {
		try {
			StringBuffer params = new StringBuffer();
			if (parameters != null) {
				Iterator it = parameters.entrySet().iterator();
				while (it.hasNext()) {
					Entry entry = (Entry)it.next();
					params.append(entry.getKey().toString() + "=");
					params.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
					if (it.hasNext()) {
						params.append("&");
					}
				}
			} 
			return params.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
//	public static String getDictionaryName(List<AssistantDictionary> list, String value) {
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getValue() != null && list.get(i).getValue().equals(value)) {
//				return list.get(i).getName();
//			}
//		}
//		return value;
//	}
	
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	public static String getDistance(double lat1, double lng1, double lat2,double lng2) {
		double dis = mathDistance(lat1, lng1, lat2, lng2);
		DecimalFormat format = new DecimalFormat("#0.00 miles");
		return format.format(dis);
	}
	public static double mathDistance(double lat1, double lng1, double lat2,double lng2) {
		double radLng1 = rad(lat1);
		double radLng2 = rad(lat2);
		double a = radLng1 - radLng2;
		double b = rad(lng1) - rad(lng2);
		double dis = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)+Math.cos(radLng1) * Math.cos(radLng2)* Math.pow(Math.sin(b / 2), 2)));
		dis = dis * 6378.137;
		dis = Math.round(dis * 10000) / 10000.0;//除数写成10000.0减小误差
		dis = dis / 1609.3;//将米转为英里
		return dis * 1000;
	}
	
	
	
	
//	public static String getVersionValue(String storeId, String platform, int type) {
//		List<Version> list = Constant.VERSION_LIST.get(storeId);
//		if(list!=null){
//		for (int i = 0; i < list.size(); i++) {
//			if (list.get(i).getPlatform().equals(platform)) {
//				if (type == 0) {
//					return list.get(i).getLowVersion();
//				} else {
//					return list.get(i).getHighVersion();
//				}
//			}
//		}
//		}
//		return "0.0.1";
//	}
	
//	public static boolean needUpdateToLatestVersion(String storeId, String platform, String curVersion) {
//		VersionUtil version = new VersionUtil(curVersion);
//		VersionUtil lowVersion = new VersionUtil(getVersionValue(storeId, platform, 0));
//		VersionUtil highVersion = new VersionUtil(getVersionValue(storeId, platform, 1));
//		if(lowVersion==null||highVersion==null){
//			return false;
//		}
//		if (version.lessThanHighVersion(highVersion) && version.greaterThanLowVersion(lowVersion)) {
//			return false;
//		}
//		if (version.greaterThanLowVersion(highVersion)) {
//			return false;
//		}
//		return true;
//	}
	
	public static List<String> getTimeList(String startTime, String endTime, int stamp){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("h:mm aaa", Locale.ENGLISH);
			Date startDateTime = sdf.parse(startTime);
			Date endDateTime = sdf.parse(endTime);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDateTime);
			Date tmpDateTime = cal.getTime();
			List<String> timeList = new ArrayList<String>();
			while (tmpDateTime.getTime() <= endDateTime.getTime()) {
				timeList.add(sdf.format(tmpDateTime));
				cal.add(cal.MINUTE, stamp);
				tmpDateTime = cal.getTime();
			}
			return timeList;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	//Eastern -5
	//Atlantic -4
	//None    0
	//Central -6
	//Mountain -7
	//Pacific -8
	//Hawaii -10
	//Samoa -11
	//UTC   0
	//Alaska -9
	 * 
	 * GMT -10:00 U.S. Hawaiian Time 
GMT -09:30 Marquesas 
GMT -09:00 U.S. Alaska Time 
GMT -08:30 Pitcarn 
GMT -08:00 Pacific Time 
GMT -07:00 U.S. Mountain Time 
GMT -07:00 U.S. Mountain Time (Arizona) 
GMT -06:00 U.S. Central Time 
GMT -06:00 Mexico 
GMT -05:00 U.S. Eastern Time 
GMT -05:00 U.S. Eastern Time (Indiana) 
	 * 
	 * @param timeZone
	 * @return
	 */
	
	public static TimeZone getTimeZone(String timeZone){
		
		
		TimeZone time = TimeZone.getDefault();// 这个是国际化所用的
		if("Eastern".equalsIgnoreCase(timeZone)){//est
			 time =TimeZone.getTimeZone("US/Eastern");//GMT-5:00  US/Eastern
		}else if("Atlantic".equalsIgnoreCase(timeZone)){
			 time =TimeZone.getTimeZone("Canada/Atlantic");//-4
		}else if("None".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("GMT+0:00");//-0
		}else if("Central".equalsIgnoreCase(timeZone)){ //cst US/Central
			time =TimeZone.getTimeZone("US/Central");//-6 GMT-6:00 
		}else if("Mountain".equalsIgnoreCase(timeZone)){//mst 
			time =TimeZone.getTimeZone("US/Mountain");//-7 GMT-7:00
		}else if("Pacific".equalsIgnoreCase(timeZone)){ //pst US/Pacific US/Pacific-New
			time =TimeZone.getTimeZone("US/Pacific-New");//-8
		}else if("Hawaii".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("GMT-10:00");//-10
		}else if("Samoa".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("GMT-11:00");//-11
		}else if("UTC".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("UTC");//0
		}else if("Alaska".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("GMT-9:00");//10
		}else if("BJ".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("Asia/Shanghai");//10
		}else if("NewYork".equalsIgnoreCase(timeZone)){
			time =TimeZone.getTimeZone("America/New_York");//
		}
		
		return time;
	}
	
	
	public static String getTimeZoneDesc(String timeZone){
		TimeZone t = getTimeZone(timeZone);
		TimeZone utc = MyUtil.getTimeZone("UTC");
		Date d=DateUtils.convertMinDate(new Date());
		//设置display
		Date newStart = DateUtils.changeTimeZone(d, t,
				utc);
		int i=(int)(d.getTime()-newStart.getTime())/3600000;
		if(i>0){
			return "UTC+" + i + ":00";
		}else{
			return "UTC" + i+ ":00";
		}
	}
	
	public static int getTimeZoneHours(String timeZone) {
		TimeZone t = getTimeZone(timeZone);
		TimeZone utc = MyUtil.getTimeZone("UTC");
		Date d=DateUtils.convertMinDate(new Date());
		//设置display
		Date newStart = DateUtils.changeTimeZone(d, t, utc);
		int i=(int)(d.getTime()-newStart.getTime())/3600000;
		return i;
	}
	
	public static int getTimeZoneInt(String timeZone){
		if("Eastern".equalsIgnoreCase(timeZone)){//est
			return -5;
		}else if("Atlantic".equalsIgnoreCase(timeZone)){
			return -4;
		}else if("None".equalsIgnoreCase(timeZone)){
			return 0;
		}else if("Central".equalsIgnoreCase(timeZone)){ //cst
			return -6;
		}else if("Mountain".equalsIgnoreCase(timeZone)){//mst
			return -7;
		}else if("Pacific".equalsIgnoreCase(timeZone)){ //pst
			return -8;
		}else if("Hawaii".equalsIgnoreCase(timeZone)){
			return -10;
		}else if("Samoa".equalsIgnoreCase(timeZone)){
			return -11;
		}else if("UTC".equalsIgnoreCase(timeZone)){
			return 0;
		}else if("Alaska".equalsIgnoreCase(timeZone)){
			return -9;
		}else if("BJ".equalsIgnoreCase(timeZone)){
			return 8;
		}
		return 0;
	}
	

	/**
	 * 
	 * @param value:0
	 * @param list:0,1,2,3,4,5,6
	 * @return
	 */
	public static String selectHtmlWeeksCheck(String value, String list) {
		if(value==null){
			return "";
		}
		if(!StringUtil.isEmpty(list)){
			if(list.indexOf(value)>-1){
				return "checked=\"checked\"";
			}
		}
		return "";
	}
	
	/**
	 * 根据传入的Request对象获取IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static void main(String[] args) {
		TimeZone t=MyUtil.getTimeZone("NewYork");
		TimeZone utc = MyUtil.getTimeZone("UTC");
		Date d=DateUtils.convertMinDate(new Date());
		//设置display
		Date newStart = DateUtils.changeTimeZone(d, t,
				utc);
		int i=(int)(d.getTime()-newStart.getTime())/3600000;
		System.out.println(i);
	}
}
