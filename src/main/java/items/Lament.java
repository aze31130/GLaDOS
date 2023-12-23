package items;

import accounts.Account;

public class Lament extends Item {
	public Lament() {
		super(1, "Lament", "TODO", Rarity.FABLED, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 50;
	}
}
