package items;

import accounts.Account;

public class WarpStone extends Item {
	public WarpStone() {
		super("Warp Stone", "minecraft:compas",
				"Deep within this stone, a glimmer of light seems to call you. The Warp Stone enables adventurers to teleport to desired destinations with unpredictable precision. Use at your own risk.",
				Rarity.MYTHICAL, 10, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 80;
	}
}
