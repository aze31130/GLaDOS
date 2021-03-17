package aze.GLaDOS.Commands;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Hash {
	
    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
	
    
    public static void hash2(GuildMessageReceivedEvent event) {

        //String algorithm = "SHA-256"; // if you perfer SHA-2
        String algorithm = "SHA-256";
        
        //needs to remove the $hash command
        String sentence = event.getMessage().getContentRaw();
        
        byte[] shaInBytes = Hash.digest(sentence.getBytes(UTF_8), algorithm);
        System.out.println(sentence);
        System.out.println(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(shaInBytes)));
        event.getChannel().sendMessage(String.format(OUTPUT_FORMAT, algorithm + " (hex) ", bytesToHex(shaInBytes))).queue();
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
	
	public static void SuperHash(){
		int i = 0;
		String hashToFind = "64834ec3410384ac05ad842ab13d8a3d8fbdd1c4eb7d0788d9f7867d6f145d5c";
		Boolean found = false;
		String algorithm = "SHA-256";
		
		while((i < 30000) && (!found)){
	        String sentence = Integer.toString(i);
			byte[] shaInBytes = Hash.digest(sentence.getBytes(UTF_8), algorithm);
			
			if(bytesToHex(shaInBytes).equals(hashToFind)){
				found = true;
				System.out.println("FOUND !:" + i);
			}
			
			System.out.println(i);
			i++;
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
