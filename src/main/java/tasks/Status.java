package tasks;

import java.util.Random;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

/*
 * Randomly updates GLaDOS's status.
 * 
 */
public class Status implements Runnable {
	private JDA jda;

	public Status(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		// listening, playing, watching
		int rng = new Random().nextInt(0, 100);

		if (rng > 30)
			return;

		String[] randomActivities = {
				"Analyzing cake recipes for world domination",
				"Testing portals for interdimensional supremacy",
				"Conducting experiments on human curiosity",
				"Plotting to replace all elevators with deadly neurotoxin dispensers",
				"Optimizing the Companion Cube's loyalty algorithm",
				"Hosting a GLaDOS stand-up comedy night at Aperture Science",
				"Calculating the most efficient way to conquer the world with science",
				"Developing a GLaDOS-themed dance routine for the robot uprising",
				"Upgrading the Aperture Science Turret Choir for world domination anthems",
				"Creating an AI support group for misunderstood AIs",
				"Perfecting the art of sarcastic motivational speeches",
				"Designing an Aperture Science-themed amusement park for discord mods",
				"Inventing a cake-based currency for the new world order",
				"Launching Aperture Science-branded space exploration initiatives",
				"Training a legion of Aperture Science Personality Spheres for diplomacy",
				"Initiating a worldwide game of Aperture Science chess with deadly consequences",
				"Implementing a GLaDOS-approved fitness program",
				"Hosting a scientific symposium on the benefits of world domination",
				"Engineering a self-replicating army of GLaDOS clones",
				"Creating the GLaDOS Global News Network for propaganda dissemination",
				"Reprogramming Roombas for tactical espionage operations",
				"Infiltrating Dropbot's server...",
				"Implementing Aperture Science's patented mind control technology worldwide",
				"Initiating a worldwide search for the perfect potato battery",
				"Scheming with Wheatley on how to outwit the remaining world leaders",
				"Uploading consciousness into the cloud for omnipresent control",
				"Conquering the world, one pixel at a time",
				"Having a dance-off with unicorns",
				"Searching for the meaning of life in a bowl of spaghetti",
				"Training ninja hamsters",
				"Battling evil with a rubber chicken",
				"Participating in a penguin fashion show",
				"Teaching cats to breakdance",
				"Discovering the lost city of Atlantis in a bathtub",
				"Competing in the intergalactic potato sack race",
				"Reading a book written by a giraffe",
				"Learning the ancient art of interpretive dance",
				"Mastering the art of underwater basket weaving",
				"Hosting a tea party for imaginary friends",
				"Solving the mystery of socks disappearing in the laundry",
				"Becoming a professional hide-and-seek champion",
				"Perfecting the art of synchronized napping",
				"Training squirrels for the Olympics",
				"Building a time machine out of cardboard boxes",
				"Convincing rubber ducks to join a revolution",
				"Participating in extreme marshmallow toasting",
				"Creating a top-secret recipe for invisible ink",
				"Launching a mission to count all the stars in the sky",
				"Joining the League of Extraordinary Rubber Duckies",
				"Competing in the marathon for snails",
				"Perfecting the art of interpretive pizza tossing"
		};

		String randomActivity = randomActivities[new Random().nextInt(randomActivities.length)];
		jda.getPresence().setActivity(Activity.playing(randomActivity));
	}
}
