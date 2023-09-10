package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.Color;

public class Zip extends Command {
    public Zip(String name, String alias, String description, String example, Boolean hidden, int permissionLevel) {
        super(name, alias, description, example, hidden, permissionLevel);
    }

    @Override
    public void execute(Argument arguments) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.ORANGE);
		embed.setTitle("Zip password");
		embed.setDescription("kdejemojehorskekolo");
        arguments.channel.sendMessage(embed.build()).queue();
    }
}
