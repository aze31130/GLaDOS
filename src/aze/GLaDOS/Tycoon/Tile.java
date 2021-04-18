package aze.GLaDOS.Tycoon;

import aze.GLaDOS.Tycoon.Building.Type;

public class Tile {
	public enum Biome {
		NONE,
		SEA,
		MONTAIN,
		PLAIN
	}
	
	private Biome biome;
	private Building building;
	
	public Tile(Biome b) {
		building = null;
		biome = b;
	}
	
	public Biome getBiome() {
		return biome;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public void build(long money, Type type) {
		switch(type) {
			case ATTRACTION:
				if(canBuild(money, Attraction.BUILD_COST)) {
					money -= Attraction.BUILD_COST;
					building = new Attraction();
				}
				break;
			case HOUSE:
				if(canBuild(money, House.BUILD_COST)) {
					money -= House.BUILD_COST;
					building = new House();
				}
				break;
			case SHOP:
				if(canBuild(money, Shop.BUILD_COST)) {
					money -= Shop.BUILD_COST;
					building = new Shop();
				}
				break;
			default:
				throw new TycoonException("Unknown building");
		}
	}
	
	private Boolean canBuild(long money, long buildCost) {
		if(!biome.equals(Biome.PLAIN)) {
			throw new TycoonException("Cannot build on non plain biome");
		}
		
		if(building != null) {
			throw new TycoonException("Cannot build on another building");
		}
		
		if(money < buildCost) {
			throw new TycoonException("Not enough money to build");
		}
		
		return true;
	}
	
	public void upgrade(long money) {
		if(building != null) {
			building.upgrade(money);
		} else {
			throw new TycoonException("Cannot upgrade a non building tile");
		}
	}
	
	public void destroy() {
		if(building != null) {
			building = null;
		} else {
			throw new TycoonException("Cannot destroy a non building tile");
		}
	}
	
	public long getAttractiveness() {
		if(isBuilding() && building.type.equals(Type.ATTRACTION)) {
			Attraction attraction = (Attraction) building;
			return attraction.getAttractiveness();
		}
		return 0;
	}
	
	public long getHousing() {
		if(isBuilding() && building.type.equals(Type.HOUSE)) {
			House house = (House) building;
			return house.getHousing();
		}
		return 0;
	}
	
	public long getIncome(long population) {
		if(isBuilding() && building.type.equals(Type.SHOP)) {
			Shop shop = (Shop) building;
			return shop.getIncome(population);
		}
		return 0;
	}
	
	private Boolean isBuilding() {
		return ((building != null) && (biome.equals(Biome.PLAIN)));
	}
}
