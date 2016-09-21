package com.besprout.voip.util;

import java.io.UnsupportedEncodingException;

public class Credentials {
	private Credentials() {
	}

	/** Returns an auth credential for the Basic scheme. */
	public static String basic(String userName, String password) {
		try {
			String usernameAndPassword = userName + ":" + password;
			byte[] bytes = usernameAndPassword.getBytes("ISO-8859-1");
			String encoded = ByteString.of(bytes).base64();
			return "Basic " + encoded;
		} catch (UnsupportedEncodingException e) {
			throw new AssertionError();
		}
	}
}
