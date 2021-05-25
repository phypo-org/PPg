package org.phypo.PPg.PPgUtils;

import java.util.regex.Pattern;

public class PPgNet {
	static final String IPV4_REGEX =
    //"^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
    //"(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";   
//	"^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
	"^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";  // Apache 
	
		
    static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX); 

	static final String IPV6_REGEX =
			//"((([0-9a-fA-F]){1,4})\\:){7}([0-9a-fA-F]){1,4}";
			"^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$"; // Apache 
	static final Pattern IPV6_PATTERN = Pattern.compile(IPV6_REGEX); 
	//-------------------------------------------	
	public static boolean VerifyIPV4( String iIp ) {
		if (iIp == null) {
			return false;
		}
		return IPV4_PATTERN.matcher(iIp).matches();
	}
	//-------------------------------------------	
	public static boolean VerifyIPV6( String iIp) {
		if (iIp == null) {
			return false;
		}
		return IPV6_PATTERN.matcher(iIp).matches();		
	}
	//-------------------------------------------	
	public static boolean VerifyIP( String iIp ) {
		return VerifyIPV4( iIp ) || VerifyIPV6( iIp );
	}

}
