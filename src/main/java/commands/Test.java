package commands;

/*
 * This command is designed to test new features
 */
public class Test extends Command {
	public Test(String name, String description, int permissionLevel) {
		super(name, description, permissionLevel);
	}

	@Override
	public void execute(Argument args) {
	}
}
