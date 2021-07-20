package org.phypo.PPg.PPgUtils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//******************************************
public class Password {

	public static int sMinSize =10;
	public static int sMinAlphaLower=1;
	public static int sMinAlphaUpper=1;
	public static int sMinDigit=1;
	public static int sMinSpecial=1;

	// inspired by https://mkyong.com/java/java-password-generator-example
	protected static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	protected static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	protected static final String CHAR_DIGIT = "0123456789";
	protected static final String CHAR_PUNCTUATION = "!@#&()–[{}]:;',?/*";
	protected static final String CHAR_SYMBOL = "~$^+=<>";
	protected static final String CHAR_SPECIAL = CHAR_PUNCTUATION + CHAR_SYMBOL;
	protected static final String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + CHAR_DIGIT + CHAR_SPECIAL;

	protected static final SecureRandom sRandom = new SecureRandom();

	//-------------------------------------------------------------------
	protected static int AddRandomString( String iInput, int iSize, StringBuilder iStr) {

		if (iInput == null || iInput.length() <= 0 || iSize < 1)
			return 0;

		for (int i = 0; i < iSize; i++) {
			iStr.append(iInput.charAt( sRandom.nextInt( iInput.length())));
		}
		return iSize;
	}
	//-------------------------------------------------------------------
	public static String ShuffleString(String iInput) {
		List<String> lResult = Arrays.asList(iInput.split(""));
		Collections.shuffle(lResult, sRandom );
		// java 8
		return lResult.stream().collect(Collectors.joining());
	}
	//-------------------------------------------------------------------
	public static String CreateAutoPassword() {

		StringBuilder lResult = new StringBuilder(sMinSize);

		AddRandomString( CHAR_LOWERCASE, sMinAlphaLower, lResult);
		AddRandomString( CHAR_UPPERCASE, sMinAlphaUpper, lResult);
		AddRandomString( CHAR_DIGIT,     sMinDigit,      lResult);
		AddRandomString( CHAR_SPECIAL,   sMinSpecial,    lResult);

		// remaining, just random
		AddRandomString( PASSWORD_ALLOW, sMinSize - lResult.length(), lResult);

		return ShuffleString( lResult.toString());
	}
	//-------------------------------------------------------------------
	public static String GetValidityConditions() {
		return "\n Password must contain at least: \n"
				+ " - " + sMinSize + " characters \n"
				+ "	- " + sMinAlphaLower +" lowercases \n"
			    + "	- " + sMinAlphaUpper +" uppercases\n"		 
			    + "	- " + sMinDigit   + " digits\n"
			    + "	- " + sMinSpecial + " special characters\n" ;
	}
	
	//-------------------------------------------------------------------
	public static String VerifyPass( String iNewPass, String iConfirmPass ) {
		if( iNewPass == null 
				||  iNewPass.length() < sMinSize ) {
			return  "Password must be at least " +  Password.sMinSize + " characters";				
		}

		int cAlphaLower=0;
		int cAlphaUpper=0;		
		int cDigit=0;
		int cSpecial=0;

		for( int i=0; i < iNewPass.length(); i++ ) {
			char c = iNewPass.charAt(i);
			if( Character.isDigit(c)) {
				cDigit++;
			} 
			else if(	 Character.isLowerCase(c) ) {
				cAlphaLower++;
			}
			else if(	 Character.isUpperCase(c) ) {
				cAlphaUpper++;
			}
			else {
				cSpecial++;
			}
		}
		if( cAlphaLower < sMinAlphaLower) {
			return "Password must contain at least " + sMinAlphaLower +" lowercases";
		}
		if( cAlphaUpper < sMinAlphaUpper) {
			return "Password must contain at least " + sMinAlphaUpper +" uppercases";
		}
		if( cDigit < sMinDigit) {
			return "Password must contain at least " + sMinDigit +" digits";
		}
		if( cSpecial < sMinSpecial) {
			return "Password must contain at least " + sMinSpecial +" special character";
		}
		if( iConfirmPass == null 
				||  iConfirmPass.length() ==0 ) {
			return "Confirm password is void";
		}

		if( iNewPass.equals( iConfirmPass ) ==false ) {
			return "Confirm password is different";
		}

		return null;
	}
}



