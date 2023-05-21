package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Resolver {
	public int PICTURE_HEIGHT;
	public int PICTURE_WIDTH;
	public int AVERAGE_RED;
	public int AVERAGE_GREEN;
	public int AVERAGE_BLUE;
	public int TOTAL_RED;
	public int TOTAL_GREEN;
	public int TOTAL_BLUE;
	public int FILE_SIZE;
	public List<Couple> Hash;
	
	public Resolver() {
		this.PICTURE_HEIGHT = 2048;
		this.PICTURE_WIDTH = 1366;
		this.AVERAGE_RED = 111;
		this.AVERAGE_GREEN = 110;
		this.AVERAGE_BLUE = 120;
		this.TOTAL_RED = 310581224;
		this.TOTAL_GREEN = 308355704;
		this.TOTAL_BLUE = 336888614;
		this.FILE_SIZE = 361053;
		this.Hash = Arrays.asList(
				new Couple("MD2", "713237ad968f5f8dca141e85d5463529"),
				new Couple("MD5", "bab81d93982f0faa2ea044ba7a01db80"),
				new Couple("SHA-1", "cd046bb214379e8feba77e5d48cd261a893fb1b4"),
				new Couple("SHA-256", "badca77dfe5feaaa7184e151878ca32b3d1d60eacd0669d1a666a720eba3d39e"),
				new Couple("SHA-384", "683ea641d65b79a2cef2aec236bd1b7c3b8dd21e7194e3d80f67a738246d40a378138e4114e324d1ada4bfc81b85ad27"),
				new Couple("SHA-512", "722522408ee185dd7f15b33f57a793a5557c351621b364d56bbfc781557cb7922ed6d3c2c80a16139027f8c7a3d6e64e15709c891cc78e95f7f4ef648d94a837"));
	}
	
	public EmbedBuilder runTest(List<Attachment> list) throws NoSuchAlgorithmException, IOException, InterruptedException {
		if(!list.isEmpty()){
			for(Attachment attachment : list){
				attachment.downloadToFile(new File("./temporaire.jpg"));
				Thread.sleep(3000);
				File file = new File("./temporaire.jpg");
				BufferedImage picture = ImageIO.read(file);
				String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Resolver Trace");
				embed.setColor(Color.DARK_GRAY);
				embed.setFooter("Request made at " + time);
				
				//Size test
				if(file.length() == FILE_SIZE) {
					embed.addField("File Size", "SUCCESS", true);
				} else {
					embed.addField("File Size", "FAILED", true);
				}
				
				//Dimension test
				if (picture.getHeight() == PICTURE_HEIGHT) {
					embed.addField("Height", "SUCCESS", true);
				} else {
					embed.addField("Height", "FAILED", true);
				}
				
				if (picture.getWidth() == PICTURE_WIDTH) {
					embed.addField("Width", "SUCCESS", true);
				} else {
					embed.addField("Width", "FAILED", true);
				}
				
				if (file.getName().endsWith(".jpg")) {
					embed.addField("File extension", "SUCCESS", true);
				} else {
					embed.addField("File extension", "FAILED", true);
				}
				
				//Hash tests
				for(Couple informations : Hash) {
					if(getFileChecksum(MessageDigest.getInstance(informations.hashName), file).equals(informations.hashOutput)) {
						embed.addField("HASH " + informations.getHashName(), "SUCCESS", true);
					} else {
						embed.addField("HASH " + informations.getHashName(), "FAILED", true);
					}
				}
				file.delete();
				return embed;
			}	
		}
		return BuildEmbed.errorEmbed("No file found");
	}
	
	private String getFileChecksum(MessageDigest digest, File file) throws IOException {
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
}
