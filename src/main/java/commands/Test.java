package commands;

public class Test extends Command {
	public Test(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example, hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
	}
}
