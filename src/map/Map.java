package map;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.Game;


public class Map {
	
	private int width;
	private int height = 15;
	
	private int level[][];
	
	public void loadMap(String s) {
		
		try {
			InputStream in = getClass().getResourceAsStream("/level/"+s+".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			width = Integer.parseInt(br.readLine());
			
			level = new int[height][width];
			
			for(int y = 0; y < height; y++) {
				String tokens[] = br.readLine().split("\\s+");
				for(int x = 0; x < width; x++) {
					level[y][x] = Integer.parseInt(tokens[x]);
				}
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void addItemFromMap() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int k = level[y][x];
				if(k == 1)
					Game.handler.addBrick(x * 32, y * 32, false);
				else if (k == 2)
					Game.handler.addBrick(x * 32, y * 32, true);
				else if (k == 3)
					Game.handler.addGoomba(x * 32, y * 32);
				else if (k == 4)
					Game.handler.addContainer(x * 32, y * 32, 0);
				else if(k == 5)
					Game.handler.addCoin(x * 32, y * 32);
			}
		}
	}
}

