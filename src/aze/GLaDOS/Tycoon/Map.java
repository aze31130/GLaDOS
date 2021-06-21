package aze.GLaDOS.tycoon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import aze.GLaDOS.tycoon.Building.Type;
import aze.GLaDOS.tycoon.Tile.Biome;

public class Map {
	public int WIDTH = 20;
	public int HEIGHT = 15;
	public Tile[][] map = new Tile[WIDTH][HEIGHT];
	
	public Map(String filename) {
		File file = new File(filename);
		//Read the file
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int x = 0;
			int y = 0;
			while((line = br.readLine()) != null) {
				x = 0;
				for(char c : line.toCharArray()) {
					switch(c) {
						case '_':
							map[x][y] = new Tile(Biome.SEA);
							break;
						case '#':
							map[x][y] = new Tile(Biome.PLAIN);
							break;
						case '^':
							map[x][y] = new Tile(Biome.MONTAIN);
							break;
						default:
							map[x][y] = new Tile(Biome.NONE);
							break;
					}
					x++;
				}
				y++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void build(int x, int y, long money, Type type) {
		map[x][y].build(money, type);
	}
	
	public void upgrade(int x, int y, long money) {
		map[x][y].upgrade(money);
	}
	
	public void destroy(int x, int y) {
		map[x][y].destroy();
	}
	
	public long getAttractiveness() {
		long attractiveness = 0;
		for(int x = 0 ; x < WIDTH ; x++) {
			for(int y = 0 ; y < HEIGHT ; y++) {
				if((map[x][y].getBuilding() != null) && (map[x][y].getBuilding().type.equals(Type.ATTRACTION))) {
					attractiveness += map[x][y].getAttractiveness();
				}
			}
		}
		return attractiveness;
	}
	
	public long getHousing() {
		long housing = 0;
		for(int x = 0 ; x < WIDTH ; x++) {
			for(int y = 0 ; y < HEIGHT ; y++) {
				if((map[x][y].getBuilding() != null) && (map[x][y].getBuilding().type.equals(Type.HOUSE))) {
					housing += map[x][y].getHousing();
				}
			}
		}
		return housing;
	}
	
	public long getPopulation() {
		if(getAttractiveness() < getHousing()) {
			return getAttractiveness();
		} else {
			return getHousing();
		}
	}
	
	public long getIncome(long population) {
		long moneyEarned = 0;
		for(int x = 0 ; x < WIDTH ; x++) {
			for(int y = 0 ; y < HEIGHT ; y++) {
				if((map[x][y].getBuilding() != null) && (map[x][y].getBuilding().type.equals(Type.SHOP))) {
					moneyEarned += map[x][y].getIncome(population);
				}
			}
		}
		return moneyEarned;
	}
}
