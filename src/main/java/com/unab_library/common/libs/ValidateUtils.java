package com.unab_library.common.libs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    private ValidateUtils() {
        // Private constructor to hide the implicit public one
    }
    /**
     * Validate if the identity document is a valid CL identity document (RUT).
     */
    public static boolean isValidChileanIdentityDocument(String identityDocument) {
        return validaRut(identityDocument);
    }

    /**
	 * 	Validate a Chilean RUT with or without dots and with or without dash
	 */
	private static Boolean validaRut ( String rut ) {
		String[] stringRut = rut.split("-");
        if (stringRut[0].length() < 7 || stringRut[0].length() > 8) return false;
		Pattern pattern = Pattern.compile("^[0-9]+-[0-9kK]{1}$");
		Matcher matcher = pattern.matcher(rut);
		if (!matcher.matches()) return false;
		return stringRut[1].toLowerCase().equals(ValidateUtils.dv(stringRut[0]));
	}
	
	/**
	 * Returns the verification digit of a rut
	 */
	public static String dv ( String rut ) {
		Integer M=0,S=1,T=Integer.parseInt(rut);
		for (;T!=0;T=(int) Math.floor(T/=10))
			S=(S+T%10*(9-M++%6))%11;
		return ( S > 0 ) ? String.valueOf(S-1) : "k";		
	}
}
