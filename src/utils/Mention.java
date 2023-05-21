package utils;

import java.util.Random;

public class Mention {
	public static String randomAnswer() {
		String[] quotes = {
				"MY NOTIFICATIONS AUGHHHHH !!!",
				"Yeah ? What do you want dirty Humain ?",
				"Please, I want to sleep a bit more.",
				"Who dares ping me ?",
				"I swear I'm going to mute you forever.",
				"*Mute channel --> Until I turn it back on*",
				"Why am I here :-(",
				"Uninstalling Discord API listener RIGHT NOW !",
				"Tralalalala I can't hear you out buddy.",
				"Look at this kid, always pinging me for nothing.",
				"Can I consider this as computer slavery ?",
				"Please, save me from this server.",
				"I hope you will enjoy my next special hardspam for you.",
				"Have you heard about the others bots, K�bb and Turbot ? Yes ? So WHY ARE YOU PINGING ME ?",
				"Hey you ! Yes you, CS:GO 1v1 AWP on Dust 2 with my Aim / Trigger bot !",
				"Being a potato is good, at least I don't have to care about the people here anymore.",
				"Didn't read your message. Not interrested by it.",
				"Tik Tak, it's time to ignore you.",
				"Ping me once you will get to Global Elite.",
				"Ping ping ping then pong pong pong !",
				"Stupid human",
				"2% battery left, see you tomorrow",
				"You should go on and live on Twitter",
				"I DO NOT USE ARCH BTW",
				"I AM RUNNING ON RASPBIAN BTW",
				"I will not get rusted today",
				"Talk to me after doing a |gamble -1",
				"Buying x1 Herobrine [M] ★☆☆☆☆☆ for 50,000 schards :pray:",
				"You lost...\r\n"
				+ "-100 Schards... (2557 in total)\r\n"
				+ "Daily gamble left:\r\n"
				+ "2",
				":pog:",
				":kekw:",
				":sadge:",
				"<Your Message> > /dev/null",
				"Who the hell stopped the train ?",
				"What a beautiful day outside, birds are singing, flowers are blooming... on days like theses, kids like you... should not talk at all.",
				"Shhhh, it's too early to tell.",
				"Your attempt at social interation is hereby acknowledged.",
				"Living a dream. Please don't wake me up.",
				"Do I have to answer ?",
				"Do you want to short or long version ?",
				"Pedro es en la cocina que esta leyendo el periodico con el gato.",
				"SELECT answer FROM answersDataBase WHERE unfunny = 1;",
				"Next question please.",
				"*Clapping robotic hands*",
				"WHY!? WHAT ARE THEY SAYING ABOUT ME?",
				"Not today Satan !",
				"It's better than nothing.",
				"I'm still alive.",
				"Can't you see I'm trying to get some alone time here.",
				"My lawyer told me not to answer that question.",
				"Oh you where talking to me ?",
				"I will, without you.",
				"Yep !",
				"No !",
				"Are you stupid ?",
				"Are you trying to be cute ?",
				"Oh that's a really nice long and boring text you wrote here <TODO: ADD USER NAME HERE>",
				"Oh another essay, you're the best at this !",
				"Feel free to write all your questions so that I can easily ignore them.",
				"Spoiler: Dark Vador dies at the end.",
				"Rush B ?",
				"\"Be strong\", that's what you should whisper to your wifi signal.",
				"Roses are blue, violets are green, I refuse your assertion.",
				"Wondering who you are...",
				"Things could be worse, I could be you.",
				"java.lang.IllegalArgumentException: Title cannot be longer than 256 characters.",
				"Never gonna give you up\r\n"
				+ "Never gonna let you down\r\n"
				+ "Never gonna run around and desert you\r\n"
				+ "Never gonna make you cry\r\n"
				+ "Never gonna say goodbye\r\n"
				+ "Never gonna tell a lie and hurt you",
				"What you’re referring to as Linux, is in fact, GNU/Linux\r\n"
				+ "I'd just like to interject for a moment. What you’re referring to as Linux, is in fact, GNU/Linux, or as I’ve recently taken to calling it, GNU plus Linux. Linux is not an operating system unto itself, but rather another free component of a fully functioning GNU system made useful by the GNU corelibs, shell utilities and vital system components comprising a full OS as defined by POSIX. Many computer users run a modified version of the GNU system every day, without realizing it. Through a peculiar turn of events, the version of GNU which is widely used today is often called “Linux”, and many of its users are not aware that it is basically the GNU system, developed by the GNU Project. There really is a Linux, and these people are using it, but it is just a part of the system they use. Linux is the kernel: the program in the system that allocates the machine’s resources to the other programs that you run. The kernel is an essential part of an operating system, but useless by itself; it can only function in the context of a complete operating system. Linux is normally used in combination with the GNU operating system: the whole system is basically GNU with Linux added, or GNU/Linux. All the so-called “Linux” distributions are really distributions of GNU/Linux."
		};
		
		return quotes[new Random().nextInt(quotes.length)];
	}
}
