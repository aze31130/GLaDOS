package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Hash {
	public static String getFileChecksum(MessageDigest digest, File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		byte[] byteArray = new byte[1024];
		int bytesCount = 0;
		while ((bytesCount = fis.read(byteArray)) != -1) {
			digest.update(byteArray, 0, bytesCount);
		}
		fis.close();
		byte[] bytes = digest.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length ; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	public static void hash(GuildMessageReceivedEvent event){
		try {
			//MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//byte[] encodedhash = digest.digest(event.getMessage().toString().getBytes(StandardCharsets.UTF_8));
			//String hash = Base64.getEncoder().encodeToString(encodedhash);
			String hash = hashWith256(event.getMessage().getContentRaw().toString());
			System.out.println(hash);
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("SHA256 hash function: " + hash).queue();
		} catch (NoSuchAlgorithmException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
	}
	
	private static String hashWith256(String textToHash) throws NoSuchAlgorithmException {
		System.out.println("text to hash:" + textToHash);
	    MessageDigest digest = MessageDigest.getInstance("SHA-256");
	    byte[] byteOfTextToHash = textToHash.getBytes(StandardCharsets.UTF_8);
	    byte[] hashedByetArray = digest.digest(byteOfTextToHash);
	    String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
	    return encoded;
	}
}
