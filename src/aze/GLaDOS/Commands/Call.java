package aze.GLaDOS.Commands;

import java.awt.Color;
import java.util.Random;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.Constants.Roles;
import aze.GLaDOS.Utils.LoadingBar;
import aze.GLaDOS.Utils.Permission;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Call {
	public static void hardSpam(GuildMessageReceivedEvent event, Member member){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(Permission.permissionLevel(member, 1)){
			if(message.length >= 3){
				int iterations = 0;
				try {
					iterations = Integer.parseInt(message[2]);
				} catch(Exception e){
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(Color.RED);
					error.setTitle("Exception !");
					error.setDescription(e.toString());
					event.getChannel().sendMessage(error.build()).queue();
				}
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Queueing " + iterations + " iterations of pinging user").queue();
				while(iterations > 0){
					event.getChannel().sendMessage(message[1]).queue();
					iterations--;
				}
			} else {
				event.getChannel().sendTyping().queue();
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(Color.RED);
				error.setTitle("Error");
				error.setDescription("The command syntax is ?hardspam (@User) <Amount>");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			event.getChannel().sendTyping().queue();
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to have the Administrator role in order to execute that.");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void Slap(TextChannel channel) {
		String[] messages = {
				""
		};
		
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		channel.sendMessage(messages[randomNumber] + " this is a test").queue();
	}
	
	public static void EpicFreeGame(TextChannel channel){
		String[] messages = {
				"BREAKING NEW: Braquage sur les serveurs d'Epicgame, depechez vous de vous y rendre: ",
				"On me dit dans l'oreillette qu'il y a un jeu gratuit. Ah oui tout a fait Jean Pierre la regie nous le confirme: ",
				"Aucun ping jusqu'a maintenant, il y anguille sous roche, allons verifier la boutique: ",
				"Est ce un oiseau ? Est ce un avion ? Non, c'est un jeu gratuit: ",
				"Oh oh oh, que vois je, un gallion avec des jeux gratuits, a l'abordage:",
				"Mon nom est gratuit, jeu gratuit JEU GRATUIT:",
				"Tout commenca lorsque les jeux furent offerts, 15 furent donnes aux hommes: ",
				"Une jeu fut offert, un jeu pour les gouverner tous: ",
				"Si vous n'allez pas au jeu gratuit, le jeu gratuit viendra a vous: ",
				"Vive le vent, vive le vent et les jeux gratuits: ",
				"Frodon, la légende raconte qu'il y a un jeu gratuit au centre de la Terre du milieu: ",
				"Et le 7ème jour, Dieu se reposera avec un jeu gratuit: ",
				"Le jeu gratuit et le gâteau sont des mensonges.",
				"Ce n'est pas n'importe quel jeu gratuit Harry, c'est un jeu Epic !",
				"Sarah Connor ? Je cherche Sarah Connor, il n'y a que des jeux gratuit ici.",
				"Quelle est ton nom ? Quelle est ta quête ? Quelle est la capitale de la Syrie ? C'est bon, tu peux avoir ton jeu gratuit.",
				"Un jeu gratuit n'est jamais en retard, Frodon Sacquet, ni en avance d'ailleurs, il arrive précisément à l'heure prévue.",
				"Je suis un ami des grands et des petits,\r\n"
				+ "avec moi s'amusent de grands amis.\r\n"
				+ "Je suis tout le temps une bonne affaire,\r\n"
				+ "avec moi vous aurez fort a faire.\r\n"
				+ "Qui suis-je ?",
				"Afin de préserver le monde de la dévastation, afin de rallier tout les peuples à notre nation, Epic, Jeu et Gratuit c'est un trio."
		};
		
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		channel.sendMessage("<@&" + Roles.GAMER.id + "> " + messages[randomNumber] + " https://www.epicgames.com/store/fr/free-games/").queue();
	}
	
	public static void LockdownDay(JDA jda){
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		Meme.randomQuotes(channel);
	}
	
	public static void MerryChristmas(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("May this season of giving be the start of your better life. Have a great and blessed holiday ! Merry christmas @everyone !").queue();
	}
	
	public static void HappyNewYear(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("Every end marks a new beginning. Keep your spirits and determination unshaken, and you shall always walk the glory road. With courage, faith and great effort, you shall achieve everything you desire. May your home gets filled with good fortune, happy New Year @everyone !").queue();
	}
	
	public static void parrot(GuildMessageReceivedEvent event, Member member){
		if(Permission.permissionLevel(member, 2)){
			TextChannel channel1 = event.getJDA().getTextChannelsByName("general-2024", true).get(0);
			TextChannel channel2 = event.getJDA().getTextChannelsByName("general-2025", true).get(0);
			String[] message = event.getMessage().getContentRaw().split("\\s+");
			String messageReady = Call.concatene2(message);
			channel1.sendMessage(messageReady).queue();
			channel2.sendMessage(messageReady).queue();
		} else {
			event.getChannel().sendTyping().queue();
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error");
			error.setDescription("You need to have the Administrator role in order to execute that.");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void maintenance() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		embed.setTitle("Scheduled maintenance");
		embed.setDescription("GLaDOS won't be available between");
		embed.setThumbnail("https://cdn.iconscout.com/icon/free/png-512/building-maintenance-2027068-1714203.png");
		embed.addField("29/01/2021", "8:00 PM CET", true);
		embed.addField("31/01/2021", "12:00 AM CET", true);
		embed.setFooter("Thanks for your patience", "https://image.flaticon.com/icons/png/512/777/777081.png");
	}
	
	public static String concatene2(String[] Table){
		String concatenated = "";
		int i = 1;
		while(i < Table.length){
			concatenated = concatenated + Table[i] + " ";
			i++;
		}
		return concatenated;
	}
	
	public static void TestFunction(JDA jda){
		//List<TextChannel> channels = jda.getTextChannelsByName("bot-snapshot", true);
		//TextChannel channels = jda.getTextChannelsByName("bots-commands", true).get(0);
		TextChannel channel = jda.getTextChannelById(Channels.BOT_SNAPSHOT.id);
		
		EmbedBuilder error = new EmbedBuilder();
		error.setColor(Color.YELLOW);
		error.setTitle("TEST");
		error.setDescription(LoadingBar.loading(0.5, 5));
		channel.sendMessage(error.build()).queue();
	}
	
	public static void THLR(JDA jda){
		//TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		//channel.sendMessage(Quotes.THLR6).queue();
		//channel.sendMessage(Quotes.THLRfinal).queue();
	}
}
