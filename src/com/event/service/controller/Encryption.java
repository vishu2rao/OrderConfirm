package com.event.service.controller;
/**
 * @author visweswarar
 *
 */
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

import com.sun.jmx.snmp.Timestamp;

/**
 * This class is a utility class to support encryption
 */
public class Encryption {

	private static final String UNICODE= "UTF8";
	public static final String DESEDE_ENCRYPTION = "DESede";
	private KeySpec ks;
	private SecretKeyFactory sf;
	private Cipher cipher;
	byte[] arrayBytes;
	private String myEncrKey;
	private String myEncryScheme;
	SecretKey key;

	public  Encryption() throws Exception {
		myEncrKey = "EventManagementisforUSmarket"; //my key it can be changed and it is going to be permanent
		myEncryScheme = DESEDE_ENCRYPTION;
		arrayBytes = myEncrKey.getBytes(UNICODE);
		ks = new DESedeKeySpec(arrayBytes);
		sf = SecretKeyFactory.getInstance(myEncryScheme);
		cipher = Cipher.getInstance(myEncryScheme);
		key = sf.generateSecret(ks);
	}

	public String encrypt(String unencryptedString) {
		String encryptedString = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = unencryptedString.getBytes(UNICODE);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public String decrypt(String encryptedString) {
		String decryptedText = null;
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			// byte[] encryptedText = Base64.decode(encryptedString);

			byte[] encryptedText = Base64.decodeBase64(encryptedString
					.getBytes());
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}
	
  public  static String stripOffEql(String str) 
  {	
	  	 
	 	    if(str.endsWith("=="))
		    {
		    	str = str.substring(0, str.length()-2);
		    }
		    if(str.endsWith("="))
		    {
		    	str = str.substring(0, str.length()-1);
		    }
     return str;
  }	 
  
  
    private static  long calculateExpiryDate(int expiryTimeInMinutes) 
	{
	        Calendar cal = Calendar.getInstance();
	       // cal.setTime(date)
	        cal.setTimeInMillis(new Timestamp(cal.getTime().getTime()).getDateTime());
	       // cal.setTimeZone(new Timestamp(cal.getTime().getTime()));
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime()).getTime();
	  }
	public static void main(String args[]) throws Exception {
		Encryption td = new Encryption();

		String target = "Vishu Rao Testing Ecncryption";
		
		// encode data on your side using BASE64
		byte[]   bytesEncoded = Base64.encodeBase64(target .getBytes());
		System.out.println("ecncoded value is " + new String(bytesEncoded ));
		
		//System.out.println("decoded String  value is " + encodeURIComponent(target));
		// Decode data on other side, by processing encoded data
		byte[] valueDecoded= Base64.decodeBase64(bytesEncoded );
		System.out.println("Decoded value is " + new String(valueDecoded));    
		
		
		String encrypted = td.encrypt(target);
		String decrypted = td.decrypt(encrypted);
		System.out.println(" To Encrypt: " + target);
		
		System.out.println("Encrypted String:" + encrypted);
		
		System.out.println("Decrypted String:" + decrypted);
		
		
		System.out.println("SettingExipry time:" + calculateExpiryDate(2000));

	}

}