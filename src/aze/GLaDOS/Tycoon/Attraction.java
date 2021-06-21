package aze.GLaDOS.tycoon;

public class Attraction extends Building {
	public final static int BUILD_COST = 10000;
	public final long[] UPGRADE_COST = {5000, 10000, 45000};
	public final long[] ATTRACTIVENESS = {500, 1000, 1300, 1500};
	private int level;
	
	public Attraction() {
		type = Type.ATTRACTION;
		level = 0;
	}

	public int getLevel() {
		return level;
	}

	public long getAttractiveness() {
		return ATTRACTIVENESS[level];
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
