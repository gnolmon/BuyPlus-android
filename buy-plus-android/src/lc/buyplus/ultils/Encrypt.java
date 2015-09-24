package lc.buyplus.ultils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	public static String encodeUTF8(final String in) {
		
		return null;
	}
	public static String decodeUTF8(final String in) {
	    String working = in;
	    int index;
	    index = working.indexOf("\\u");
	    while(index > -1) {
	        int length = working.length();
	        if(index > (length-6))break;
	        int numStart = index + 2;
	        int numFinish = numStart + 4;
	        String substring = working.substring(numStart, numFinish);
	        int number = Integer.parseInt(substring,16);
	        String stringStart = working.substring(0, index);
	        String stringEnd   = working.substring(numFinish);
	        working = stringStart + ((char)number) + stringEnd;
	        index = working.indexOf("\\u");
	    }
	    return working;
	}
	public static String encryptMD5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	public static String encryptCaesar(int key) {
		return null;
	}
	public static String decryptCaesar(int key) {
		return null;
	}
}
