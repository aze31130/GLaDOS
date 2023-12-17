package items;

import accounts.Account;

public class Fatal extends Item {
	public Fatal() {
		super("Fatal", "minecraft:diamond_sword",
				"TODO",
				Rarity.FABLED, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 50;
	}
}
