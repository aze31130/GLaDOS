package aze.GLaDOS.tycoon;

public class Shop extends Building {
	public final static int BUILD_COST = 300;
	public final long[] UPGRADE_COST = {2500, 10000, 50000};
	public final long[] INCOME = {7, 8, 9, 10};
	private int level;
	
	public Shop() {
		type = Type.SHOP;
		level = 0;
	}

	public int getLevel() {
		return level;
	}

	public long getIncome(long population) {
		return ((INCOME[level] * population) / 100);
	}
	
	public void upgrade(long money) {
		if((level < MAX_LEVEL)) {
			if(UPGRADE_COST[level] <= money) {
				money -= UPGRADE_COST[level];
				level++;
			} else {
				throw new TycoonException("Not enough money to upgrade");
			}
		} else {
			throw new TycoonException("Cannot upgrade beyond max level");
		}	
	}
}
