package com.besprout.voip.util;

public class ByteString {
	static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final long serialVersionUID = 1L;

	/** A singleton empty {@code ByteString}. */
	public static final ByteString EMPTY = ByteString.of();

	final byte[] data;
	transient int hashCode; // Lazily computed; 0 if unknown.
	transient String utf8; // Lazily computed.

	ByteString(byte[] data) {
		this.data = data; // Trusted internal constructor doesn't clone data.
	}

	/**
	 * Returns a new byte string containing a clone of the bytes of {@code data}
	 * .
	 */
	public static ByteString of(byte... data) {
		if (data == null)
			throw new IllegalArgumentException("data == null");
		return new ByteString(data.clone());
	}


	/**
	 * Returns this byte string encoded as
	 * <a href="http://www.ietf.org/rfc/rfc2045.txt">Base64</a>. In violation of
	 * the RFC, the returned string does not wrap lines at 76 columns.
	 */
	public String base64() {
		return Base64.encode(data);
	}

	/**
	 * Returns this byte string encoded as
	 * <a href="http://www.ietf.org/rfc/rfc4648.txt">URL-safe Base64</a>.
	 */
	public String base64Url() {
		return Base64.encodeUrl(data);
	}

	/**
	 * Decodes the Base64-encoded bytes and returns their value as a byte
	 * string. Returns null if {@code base64} is not a Base64-encoded sequence
	 * of bytes.
	 */
	public static ByteString decodeBase64(String base64) {
		if (base64 == null)
			throw new IllegalArgumentException("base64 == null");
		byte[] decoded = Base64.decode(base64);
		return decoded != null ? new ByteString(decoded) : null;
	}

	/** Returns this byte string encoded in hexadecimal. */
	public String hex() {
		char[] result = new char[data.length * 2];
		int c = 0;
		for (byte b : data) {
			result[c++] = HEX_DIGITS[(b >> 4) & 0xf];
			result[c++] = HEX_DIGITS[b & 0xf];
		}
		return new String(result);
	}

	/** Decodes the hex-encoded bytes and returns their value a byte string. */
	public static ByteString decodeHex(String hex) {
		if (hex == null)
			throw new IllegalArgumentException("hex == null");
		if (hex.length() % 2 != 0)
			throw new IllegalArgumentException("Unexpected hex string: " + hex);

		byte[] result = new byte[hex.length() / 2];
		for (int i = 0; i < result.length; i++) {
			int d1 = decodeHexDigit(hex.charAt(i * 2)) << 4;
			int d2 = decodeHexDigit(hex.charAt(i * 2 + 1));
			result[i] = (byte) (d1 + d2);
		}
		return of(result);
	}

	private static int decodeHexDigit(char c) {
		if (c >= '0' && c <= '9')
			return c - '0';
		if (c >= 'a' && c <= 'f')
			return c - 'a' + 10;
		if (c >= 'A' && c <= 'F')
			return c - 'A' + 10;
		throw new IllegalArgumentException("Unexpected hex digit: " + c);
	}


	/**
	 * Returns a byte string equal to this byte string, but with the bytes 'A'
	 * through 'Z' replaced with the corresponding byte in 'a' through 'z'.
	 * Returns this byte string if it contains no bytes in 'A' through 'Z'.
	 */
	public ByteString toAsciiLowercase() {
		// Search for an uppercase character. If we don't find one, return this.
		for (int i = 0; i < data.length; i++) {
			byte c = data[i];
			if (c < 'A' || c > 'Z')
				continue;

			// If we reach this point, this string is not not lowercase. Create
			// and
			// return a new byte string.
			byte[] lowercase = data.clone();
			lowercase[i++] = (byte) (c - ('A' - 'a'));
			for (; i < lowercase.length; i++) {
				c = lowercase[i];
				if (c < 'A' || c > 'Z')
					continue;
				lowercase[i] = (byte) (c - ('A' - 'a'));
			}
			return new ByteString(lowercase);
		}
		return this;
	}

	/**
	 * Returns a byte string equal to this byte string, but with the bytes 'a'
	 * through 'z' replaced with the corresponding byte in 'A' through 'Z'.
	 * Returns this byte string if it contains no bytes in 'a' through 'z'.
	 */
	public ByteString toAsciiUppercase() {
		// Search for an lowercase character. If we don't find one, return this.
		for (int i = 0; i < data.length; i++) {
			byte c = data[i];
			if (c < 'a' || c > 'z')
				continue;

			// If we reach this point, this string is not not uppercase. Create
			// and
			// return a new byte string.
			byte[] lowercase = data.clone();
			lowercase[i++] = (byte) (c - ('a' - 'A'));
			for (; i < lowercase.length; i++) {
				c = lowercase[i];
				if (c < 'a' || c > 'z')
					continue;
				lowercase[i] = (byte) (c - ('a' - 'A'));
			}
			return new ByteString(lowercase);
		}
		return this;
	}

	/**
	 * Returns a byte string that is a substring of this byte string, beginning
	 * at the specified index until the end of this string. Returns this byte
	 * string if {@code beginIndex} is 0.
	 */
	public ByteString substring(int beginIndex) {
		return substring(beginIndex, data.length);
	}

	/**
	 * Returns a byte string that is a substring of this byte string, beginning
	 * at the specified {@code beginIndex} and ends at the specified
	 * {@code endIndex}. Returns this byte string if {@code beginIndex} is 0 and
	 * {@code endIndex} is the length of this byte string.
	 */
	public ByteString substring(int beginIndex, int endIndex) {
		if (beginIndex < 0)
			throw new IllegalArgumentException("beginIndex < 0");
		if (endIndex > data.length) {
			throw new IllegalArgumentException("endIndex > length(" + data.length + ")");
		}

		int subLen = endIndex - beginIndex;
		if (subLen < 0)
			throw new IllegalArgumentException("endIndex < beginIndex");

		if ((beginIndex == 0) && (endIndex == data.length)) {
			return this;
		}

		byte[] copy = new byte[subLen];
		System.arraycopy(data, beginIndex, copy, 0, subLen);
		return new ByteString(copy);
	}

	/** Returns the byte at {@code pos}. */
	public byte getByte(int pos) {
		return data[pos];
	}

	/**
	 * Returns the number of bytes in this ByteString.
	 */
	public int size() {
		return data.length;
	}

	/**
	 * Returns a byte array containing a copy of the bytes in this
	 * {@code ByteString}.
	 */
	public byte[] toByteArray() {
		return data.clone();
	}

	/**
	 * Returns the bytes of this string without a defensive copy. Do not mutate!
	 */
	byte[] internalArray() {
		return data;
	}


}
