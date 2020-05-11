package RedT;

// API ZingMp3
// Code by RedT

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.*;

import java.net.URLEncoder;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helpers {
	private static final String MAIN_URL    = "https://zingmp3.vn";
	private static final String XHR_URL     = "https://m.zingmp3.vn/xhr";
	private static final String API_V5_URL  = "https://m.zingmp3.vn/api";
	private static final String MP3_STATIC  = "https://static-zmp3.zadn.vn/skins/zmp3-mobile-v5.2";
	private static final String PHOTO_URL   = "https://photo-zmp3.zadn.vn/";

	public static final String URL_DETAIL       = "/chart-realtime/get-detail";
	public static final String URL_SOUNG_INFO   = "/song/get-song-info";
	public static final String URL_LYRIC        = "/song/get-lyric";
	public static final String URL_STREAMMING   = "/song/get-streamings";
	public static final String URL_PLAYLIST     = "/playlist/get-playlist-detail";
	public static final String URL_SEARCH       = "/api/search";
	public static final String URL_SEARCH_MULTI = "/search/multi";

	public static final String REGEX_URL    = "(https?:)?\\/\\/(.*)";

	private static final String HMAC_SHA512 = "HmacSHA512";
	public static final String API_KEY      = "38e8643fb0dc04e8d65b99994d3dafff";
	public static final String PRIVATE_KEY  = "10a01dcf33762d3a204cb96429918ff6";
	public static int ctime   = 1589168845;
	public static String type = "song";
	public  static String sig = "";

	// Sort by Key HashMap
	public static Map<String, String> sortByKey(Map<String, String> map) {
		TreeMap<String, String> sorted = new TreeMap<>(map);
		return sorted;
	}

	// SHA 256
	public static String toHexStringSHA256(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}
	public static String getSHA256(String input) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return toHexStringSHA256(md.digest(input.getBytes(StandardCharsets.UTF_8)));
	}

	// MAC SHA512
	private static String toHexStringMAC512(byte[] bytes) {
		Formatter formatter = new Formatter();
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
	public static String calculateHMAC(String data, String key) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), HMAC_SHA512);
		Mac mac = Mac.getInstance(HMAC_SHA512);
		mac.init(secretKeySpec);
		return toHexStringMAC512(mac.doFinal(data.getBytes()));
	}

	public static String getCurrentTime(){
		Date date = new Date();
		return String.valueOf(date.getTime()/1000);
	}

	// BUILD URL
	public static String buildAPIURL(String api, Map<String, String> param) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		param.put("ctime", getCurrentTime());
		param.put("sig", buildSig(api, param));
		return API_V5_URL + api + "?" + buildQuery(param, "&") + "api_key=" + API_KEY;
	}

	public static String buildSig(String api, Map<String, String> param) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException {
		String n = buildMessage(param);
		sig = calculateHMAC(api + getSHA256(n), PRIVATE_KEY);
		return sig;
	}

	// buildQuery
	public static String buildQuery(Map<String, String> param, String sq){
		String cer = "";
		for (String key : param.keySet()) {
			cer += key + "=" + URLEncoder.encode(param.get(key)) + sq;
		}
		return cer;
	}

	public static String buildMessage(Map<String, String> param){
		param = sortByKey(param);

		Map<String, String> newCer = new HashMap<String, String>();
		for (String key : param.keySet()) {
			if ((key.equals("ctime") || key.equals("id")) && null != param.get(key)) {
				newCer.put(key, param.get(key));
			}
		}
		return buildQuery(newCer, "");
	}

	// add https to url
	public static String patternURL(String url){
		Pattern pattern = Pattern.compile(REGEX_URL);
		Matcher matcher = pattern.matcher(url);
		if(matcher.find()) {
			url = matcher.group(2);
			if(!url.isEmpty()){
				url = "https://" + url;
			}
		}
		return url;
	}
}
