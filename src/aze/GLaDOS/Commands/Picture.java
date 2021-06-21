package aze.GLaDOS.commands;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import aze.GLaDOS.utils.Counter;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Picture {
	public static void inverse(GuildMessageReceivedEvent event){
		
		List<Attachment> list = event.getMessage().getAttachments();
		//Message.Attachment attachment = event.getMessage().getAttachments().get(0);
		if(!list.isEmpty()){
			for(Attachment attachment : list){
				if((attachment.getFileExtension().equalsIgnoreCase("jpg"))){
					attachment.downloadToFile(new File("./temporaire.jpg"));
					
					
					event.getChannel().sendMessage("Starting processing !").queue();
					BufferedImage img = null;
					
					try{
						TimeUnit.SECONDS.sleep(3);
					} catch (Exception exception){
						System.out.println("Wait");
					}
					
					try {
						img = ImageIO.read(new File("./temporaire.jpg"));
					} catch (IOException exception) {
						event.getChannel().sendMessage(exception.toString());
			        }
					int height = img.getHeight();
					int width = img.getWidth();
					//int alpha = 255;
					int red = 0;
					int green = 0;
					int blue = 0;
					//int argb;
					
					Color color;
					
					
					for (int h = 0 ; h < height ; h++){
						for (int w = 0 ; w < width ; w++){
							
							color = new Color(img.getRGB(w, h));
							
							red = 255 - color.getRed();
							green = 255 - color.getGreen();
							blue = 255 - color.getBlue();
							//rgb = img.getRGB(startX, startY, w, width, rgbArray, offset, scansize)
							
							if (red < 0){
								red = 0;
							}
							
							if (green < 0){
								green = 0;
							}
							
							if (blue < 0){
								blue = 0;
							}
							
							//argb = alpha << 24 + red << 16 + green << 8 + blue;
							
							//img.setRGB(w, h, argb);
							img.setRGB(w, h, new Color(red, green, blue).getRGB());
						}
					}
	
			        try {
			        	File outputfile = new File("temporaire.jpg");
			            ImageIO.write(img, "jpg", outputfile);
				        event.getChannel().sendFile(outputfile).queue();
						try{
							TimeUnit.SECONDS.sleep(3);
						} catch (Exception exception){
							System.out.println("Wait");
						}
						outputfile.delete();
			        } catch (IOException e) {
			        	event.getChannel().sendMessage(e.toString());
			        }
				} else {
					event.getChannel().sendMessage("Cannot handle " + attachment.getFileExtension() + " files !").queue();
				}
			}
		} else {
			event.getChannel().sendMessage("0 attachment found !").queue();
		}
	}
	
	public static void Save(TextChannel channel) {
		Counter count = new Counter();
		channel.getIterableHistory().cache(false).forEachRemaining((message) -> {
			if(message.getAttachments().size() > 0) {
				for(Attachment attachment : message.getAttachments()){
					if(attachment.isImage()) {
						attachment.downloadToFile(new File("./test/" + attachment.getFileName()));
						count.add();
					}
				}
			}
	    	return true;
	    });
		channel.sendMessage("Downloaded " + count.value() + "pictures !").queue();
		
	}
}
