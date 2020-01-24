package logika;

import java.util.Random;

public class SpravceHry {
	private boolean[][] miny = new boolean[10][10];
	private final int pocetMin = 10;
	
	public boolean[][] getMiny(){
		return miny;
	}
	
	public int getPocetMin() {
		return pocetMin;
	}
	
	public SpravceHry() {
		umistiMiny(pocetMin);
	}
	
	public void umistiMiny(int pocetMin) {
		Random random = new Random();
		for (int i=0; i<pocetMin; i++) {
			int x = random.nextInt(10);
			int y = random.nextInt(10);
			while (miny[x][y]) {
				x = random.nextInt(10);
				y = random.nextInt(10);
			}
			miny[x][y] = true;
		}
	}
	
	
	public int zjistiPocetOkolnich(int x, int y) {
		int pocetOkolnich = 0;
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if ((x + j) >= 0 && (y + i) >= 0 && (x + j) < 10 && (y + i) < 10) {
					if (miny[x+j][y+i])
						pocetOkolnich++;
				}
			}
		}
		return pocetOkolnich;
	}
	
}
