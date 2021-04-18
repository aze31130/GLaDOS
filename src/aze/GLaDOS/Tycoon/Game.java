package aze.GLaDOS.Tycoon;

import java.io.BufferedReader;
import java.io.FileReader;

import aze.GLaDOS.Tycoon.Building.Type;

public class Game {
	private final int MAX_ROUND = 99;
	private int CURRENT_ROUND = 0;
	private final int INITIAL_MONEY = 11000;
	private long MONEY = INITIAL_MONEY;
	private Map map;
	private String[] orders = new String[100];
	
	public Game(String m, String bot) {
		map = new Map(m);
		readOrders(bot); 
	}
	
	public long getScore() {
		return MONEY - INITIAL_MONEY;
	}
	
	public int getRound() {
		return CURRENT_ROUND;
	}
	
	public void readOrders(String botFilename) {
		try(BufferedReader br = new BufferedReader(new FileReader(botFilename))) {
			String line;
			int i = 0;
			while((line = br.readLine()) != null) {
				orders[i] = line;
				i++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(this.CURRENT_ROUND < this.MAX_ROUND) {
			update(this.CURRENT_ROUND);
			this.CURRENT_ROUND++;
		}
	}
	
	public void update(int currentRound) {
		//Get all the orders for the round and execute them
		for(String s : orders[currentRound].split("[|]+")) {
			executeOrder(s);
		}
		
		//Update the money
		MONEY += map.getIncome(map.getPopulation());
	}
	
	public void executeOrder(String order) {
		//Split the order
		String[] segmented = order.split("\\s+");
		if((segmented.length > 2) && !isValidCoords(segmented[segmented.length - 2], segmented[segmented.length - 1])) {
			throw new TycoonException("Invalid coordinates");
		}
		
		switch(segmented[0]) {
			case "B":
				//Build Order
				if(segmented.length == 4) {
					switch(segmented[1]) {
						case "1":
							map.build(Integer.parseInt(segmented[2]), Integer.parseInt(segmented[3]),
									this.MONEY, Type.ATTRACTION);
							break;
						case "2":
							map.build(Integer.parseInt(segmented[2]), Integer.parseInt(segmented[3]),
									this.MONEY, Type.HOUSE);
							break;
						case "3":
							map.build(Integer.parseInt(segmented[2]), Integer.parseInt(segmented[3]),
									this.MONEY, Type.SHOP);
							break;
						default:
							throw new TycoonException("Unknown building");
					}
				}
				break;
			case "D":
				//Destroy order
				if(segmented.length == 3) {
					map.destroy(Integer.parseInt(segmented[1]), Integer.parseInt(segmented[2]));
				}
				break;
			case "U":
				//Upgrade order
				if(segmented.length == 3) {
					map.upgrade(Integer.parseInt(segmented[1]), Integer.parseInt(segmented[2]), this.MONEY);
				}
				break;
			//Case to skip a round
			case "":
				break;
			default:
				throw new TycoonException("Unknown order");
		}
	}
	
	public Boolean isValidCoords(String x, String y) {
		if((Integer.parseInt(x) >= 0) && (Integer.parseInt(x) < map.WIDTH) &&
				(Integer.parseInt(y) >= 0) && (Integer.parseInt(y) < map.HEIGHT)) {
			return true;
		} else {
			return false;
		}
	}
}
