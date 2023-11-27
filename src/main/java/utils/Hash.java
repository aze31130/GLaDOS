package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hash {
	private static String hashWith256(String textToHash) throws NoSuchAlgorithmException {
		System.out.println("text to hash:" + textToHash);
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
		byte[] hashedByetArray = digest.digest(byteOfTextToHash);
		String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
		return encoded;
	}
}
