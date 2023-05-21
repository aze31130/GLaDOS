package commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import utils.EmoteCounter;
import net.dv8tion.jda.api.entities.TextChannel;

public class Statistics extends Command {
	public Statistics(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example,
				hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		List<EmoteCounter> ec = new ArrayList<EmoteCounter>();
		
		/*
		for(TextChannel ch : args.member.getJDA().getGuilds().get(1).getTextChannels()) {
			System.out.println("Starting " + ch.getName());
			Stats.ListChannelMessage(ch, ec);
			System.out.println("DONE");
		}
		*/
		
		TextChannel c = args.member.getJDA().getTextChannelById("676731153444765709");
		c.sendMessage("Starting computing stats of channel <#" + c.getId() + ">").queue();
		
		//Stats.ListChannelMessage(args.member.getJDA().getTextChannelById("699751218179997816"), ec);
		Stats.ListChannelMessage(c, ec);
		
		ec.sort(Comparator.comparingInt(EmoteCounter::getAmount).reversed());
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream("./stats-676731153444765709.txt"), "UTF-8"));

			for(EmoteCounter e : ec) {
				out.write(e.name + ":" + e.count + "\n");
			}
			out.close();
			File f = new File("./stats-676731153444765709.txt");
			c.sendFile(f, "stats-676731153444765709.txt").queue();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 	public static void statistics(TextChannel channel, Member member) {
		if(Permission.permissionLevel(null, member, 1)){
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			channel.sendMessage("Calculating stats of channel: " + channel.getAsMention()).queue();
			int i = 0;
			int TotalMessage = 0;
			List<Pair> pairList = new ArrayList<>();
			Counter count = new Counter();
			channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
				//Scan the list
				int j = 0;
				boolean found = false;
				while((j < pairList.size()) && (!found)) {
					if(pairList.get(j).author.equals(me.getAuthor())) {
						pairList.get(j).messageAmount++;
						count.add();
						found = true;
					}
					j++;
				}
				
				//If the user isn't found add him with one as amount of message
				if(!found) {
					pairList.add(new Pair(me.getAuthor(), 1));
				}
				
				if(count.value() % 250 == 0) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						channel.sendMessage(e.toString()).queue();
						e.printStackTrace();
					}
				}
		    	return pairList.size() < Constants.MAX_RETRIEVE_MESSAGES;
		    });
			
			i = 0;
			while(i < pairList.size()) {
				TotalMessage += pairList.get(i).messageAmount;
				i++;
			}
			
			pairList.sort(Comparator.comparingInt(Pair::getAmount).reversed());
			EmbedBuilder result = new EmbedBuilder();
			result.setColor(Color.YELLOW);
			result.setTitle("Message contribution for channel: " + channel.getName());
			result.setDescription("Total Message: " + TotalMessage);
			i = 0;
			String resultString = "";
			for (Pair pair : pairList){
				resultString += Integer.toString(pair.messageAmount) + " ("+ ((float)(pair.messageAmount * 100) / TotalMessage) +"%) " + pair.author.getName() + "\n";
				i++;
			}
			result.setFooter("Request made at " + time);
			
			try {
				FileWriter file = new FileWriter("./general-stats.txt");
				file.write(resultString);
				file.flush();
				file.close();
			} catch (IOException e) {
				channel.sendMessage(BuildEmbed.errorEmbed(e.toString()).build()).queue();
				e.printStackTrace();
			}	
			channel.sendMessage(result.build()).queue();
			channel.sendMessage(resultString).queue();
		} else {
			channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
	 * 
	 * 
	 */
}
