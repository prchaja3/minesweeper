package minesweeper;

import logika.SpravceHry;

public class Minesweeper {

	public static void main(String[] args) {
		SpravceHry spravce = new SpravceHry();
		
		Okno okno = new Okno(spravce);
		okno.setSize(500, 500);
		okno.setVisible(true);
		
	}

}
