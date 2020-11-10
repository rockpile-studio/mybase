package studio.rockpile.mybase.util;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Random;

public class SimpleEncoder {
	private static final int RADIX = 16;
	private static final String SEED = "0933910847463829827159347601486730416058";
	public static final int ENCRYPTED_PREFIX_LENGTH = 8;
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String md5(String text) throws Exception {
		// 生成32位小写的MD5信息摘要
		byte[] bytes = MessageDigest.getInstance("md5").digest(text.getBytes());
		return bytesToHex(bytes);
	}

	public static String bytesToHex(byte... bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] hexToBytes(String hex) {
		hex = hex.length() % 2 != 0 ? "0" + hex : hex;

		byte[] b = new byte[hex.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(hex.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}

	public static String getRandomString(int length) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < length; i++) {
			int type = new Random().nextInt(3);
			long random = 0;
			switch (type) {
			case 0:
				random = Math.round(Math.random() * 25 + 65);
				buff.append(String.valueOf((char) random));
				break;
			case 1:
				random = Math.round(Math.random() * 25 + 97);
				buff.append(String.valueOf((char) random));
				break;
			case 2:
				random = new Random().nextInt(10);
				buff.append(String.valueOf(random));
				break;
			}
		}
		return buff.toString();
	}

	public static final String encrypt(String message) {
		return SimpleEncoder.encrypt(message, "UTF-8");
	}

	public static final String encrypt(String message, String charset) {
		if (message == null)
			return "";
		if (message.length() == 0) {
			return "";
		} else {
			String code = getRandomString(ENCRYPTED_PREFIX_LENGTH) + message;
			BigInteger bi_passwd = new BigInteger(code.getBytes(Charset.forName(charset)));
			BigInteger bi_r0 = new BigInteger(SEED);
			BigInteger bi_r1 = bi_r0.xor(bi_passwd);
			return bi_r1.toString(RADIX);
		}
	}

	public static final String decrypt(String encrypted) {
		return SimpleEncoder.decrypt(encrypted, "UTF-8");
	}

	public static final String decrypt(String encrypted, String charset) {
		if (encrypted == null)
			return "";
		if (encrypted.length() == 0)
			return "";
		BigInteger bi_confuse = new BigInteger(SEED);
		try {
			BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
			BigInteger bi_r0 = bi_r1.xor(bi_confuse);
			String decode = new String(bi_r0.toByteArray(), Charset.forName(charset));
			return decode.substring(ENCRYPTED_PREFIX_LENGTH);
		} catch (Exception e) {
			return "";
		}
	}
}
