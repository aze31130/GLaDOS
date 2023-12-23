package items;

import accounts.Account;

public class WhiteWhistle extends Item {
	public WhiteWhistle() {
		super(11, "White Whistle",
				"TODO",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
