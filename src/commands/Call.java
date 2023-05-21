package commands;

import java.awt.Color;
import java.util.Random;
import constants.Constants.Channels;
import constants.Constants.Roles;
import utils.LoadingBar;
import utils.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public class Call {
	
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
				"Games don't make you violent, lag does",
				"We are not players, we are gamerz",
				"Failure doesn't mean the game is over, it means try again with more experience",
				"A true Gamerz doesn't have birthdays, He level up !",
				"I don't need to get a life, I am a Gamer, I have a lots of lives.",
				"Just one more game UwU",
				"A hero need not speak. When he is gone, the world will speak for him.",
				"Alert ! The bomb has been planted. 40 seconds to detonation.",
				"If I cannot outsmart them, I will outfight them.",
				"Games are the only legal place to kill stupids.",
				"They told me I couldn�t, that�s why I did.",
				"Video games foster the mindset that allows creativity to grow.",
				"When life gives you curdled milk, be patient, you get very good cheese.",
				"Yes! I am a gamer, but surely I don�t play until you start gaming.",
				"Escape reality and play games.",
				"When I play fighting games, I press random buttons and hope for the rest.",
				"Maturity is when you realize Winner Winner Chicken Dinner is not a great success.",
				"Life is a game, the game of life ! Play to win.",
				"We don�t stop playing because we grow old, We grow old because we stop playing.",
				"We all make choices in life, but in the end our choices make us.",
				"Eat, Sleep, Play repeat.",
				"Gamers don�t die they respawn.",
				"I am not a player, I am a gamer. Players get a chick, I get the achievement.",
				"Don�t play the game. Win it.",
				"A sword wields no strength unless the hands that holds it has courage.",
				"It’s a funny thing, ambition. It can take one to sublime heights or harrowing depths. And sometimes they are one and the same.",
				"You can’t break a man the way you break a dog, or a horse. The harder you beat a man, the taller he stands.",
				"True grace is beautiful in its imperfection, honest in its emotion, freed by its on reality.",
				"If history is to change, let it change. If the world is to be destroyed, so be it. If my fate is to die, I must simply laugh.",
				"Even in dark times, we cannot relinquish the things that make us human.",
				"A strong man doesn’t need to read the future. He makes his own.",
				"What is better – To be born good, or to overcome your evil nature through great effort?",
				"However, that parting need not last forever... Whether a parting be forever or merely for a short time... That is up to you.",
				"Hatred and prejudice will never be eradicated. And the witch hunts will never be about witches. To have a scapegoat, that's the key.",
				"Life is a negotiation. We all want. We all give to get what we want.",
				"Does this unit have a soul?",
				"I had no choice, I had to do it…I only see the opportunity. But when I’m gone, everyone’s gonna remember my name: Big Smoke!",
				"Always fear the flame, lest you be devoured by it, and lose yourself.",
				"I am a man of unfortunate and I must seek my fortune.",
				"A famous explorer once said, that the extraordinary is in what we do, not who we are.",
				"Often when we guess at others' motives, we reveal only our own.",
				"If history only remembers one in thousands of us, then the future will be filled with stories of who we were and what we did.",
				"The more things change, the more they stay the same.",
				"I'm the leading man. Do you know what they say about the leading man? He never dies.",
				"I don't like to lose at all.",
				"Everyone has played video games at some point these days, and video games are fun.",
				"I do miss playing on the big stage, to be able to hear the crowd roar in the stadium. But I don't miss competing."
		};
		
		/*
		 * "BREAKING NEW: Braquage sur les serveurs d'Epicgame, depechez vous de vous y rendre: ",
				"On me dit dans l'oreillette qu'il y a un jeu gratuit. Ah oui tout a fait Jean Pierre la regie nous le confirme: ",
				"Aucun ping jusqu'a maintenant, il y anguille sous roche, allons verifier la boutique: ",
				"Est ce un oiseau ? Est ce un avion ? Non, c'est un jeu gratuit: ",
				"Oh oh oh, que vois je, un gallion avec des jeux gratuits, a l'abordage:",
				"Mon nom est gratuit, jeu gratuit JEU GRATUIT:",
				"Tout commenca lorsque les jeux furent offerts, 15 furent donnes aux hommes: ",
				"Une jeu fut offert, un jeu pour les gouverner tous: ",
				"Si vous n'allez pas au jeu gratuit, le jeu gratuit viendra a vous: ",
				"Vive le vent, vive le vent et les jeux gratuits: ",
				"Frodon, la l�gende raconte qu'il y a un jeu gratuit au centre de la Terre du milieu: ",
				"Et le 7�me jour, Dieu se reposera avec un jeu gratuit: ",
				"Le jeu gratuit et le g�teau sont des mensonges.",
				"Ce n'est pas n'importe quel jeu gratuit Harry, c'est un jeu Epic !",
				"Sarah Connor ? Je cherche Sarah Connor, il n'y a que des jeux gratuit ici.",
				"Quelle est ton nom ? Quelle est ta qu�te ? Quelle est la capitale de la Syrie ? C'est bon, tu peux avoir ton jeu gratuit.",
				"Un jeu gratuit n'est jamais en retard, Frodon Sacquet, ni en avance d'ailleurs, il arrive pr�cis�ment � l'heure pr�vue.",
				"Je suis un ami des grands et des petits,\r\n"
				+ "avec moi s'amusent de grands amis.\r\n"
				+ "Je suis tout le temps une bonne affaire,\r\n"
				+ "avec moi vous aurez fort a faire.\r\n"
				+ "Qui suis-je ?",
				"Afin de pr�server le monde de la d�vastation, afin de rallier tout les peuples � notre nation, Epic, Jeu et Gratuit c'est un trio."
		 * 
		 */
		
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		
		EmbedBuilder game = new EmbedBuilder()
				.setColor(Color.BLACK)
				.setAuthor("www.epicgames.com", "https://www.epicgames.com/store/fr/free-games/")
				.setTitle(messages[randomNumber])
				.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/3/31/Epic_Games_logo.svg/1200px-Epic_Games_logo.svg.png")
				.setFooter("Request made at " + new Logger(false));
		channel.sendMessage("<@&" + Roles.GAMER.id + ">").queue();
		channel.sendMessage(game.build()).queue();
		
		//channel.sendMessage("<@&" + Roles.GAMER.id + "> " + messages[randomNumber] + " https://www.epicgames.com/store/fr/free-games/").queue();
	}
	
	public static void MerryChristmas(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("May this season of giving be the start of your better life. Have a great and blessed holiday ! Merry christmas @everyone !").queue();
	}
	
	public static void HappyNewYear(JDA jda) {
		TextChannel channel = jda.getTextChannelsByName("general", true).get(0);
		channel.sendMessage("Every end marks a new beginning. Keep your spirits and determination unshaken, and you shall always walk the glory road. With courage, faith and great effort, you shall achieve everything you desire. May your home gets filled with good fortune, happy New Year @everyone !").queue();
	}
	
	public static EmbedBuilder maintenance() {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.ORANGE);
		embed.setTitle("Scheduled maintenance");
		embed.setDescription("GLaDOS won't be available between");
		embed.setThumbnail("https://cdn.iconscout.com/icon/free/png-512/building-maintenance-2027068-1714203.png");
		embed.addField("14/05/2021", "8:00 PM CEST", true);
		embed.addField("16/05/2021", "12:00 PM CEST", true);
		embed.setFooter("Thanks for your patience", "https://image.flaticon.com/icons/png/512/777/777081.png");
		return embed;
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
