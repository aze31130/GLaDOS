package tycoon;

public class House extends Building {
	public final static int BUILD_COST = 250;
	public final long[] UPGRADE_COST = {750, 3000, 10000};
	public final long[] HOUSING = {300, 500, 650, 750};
	private int level;
	
	public House() {
		type = Type.HOUSE;
		level = 0;
	}

	public int getLevel() {
		return level;
	}

	public long getHousing() {
		return HOUSING[level];
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
