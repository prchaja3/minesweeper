package minesweeper;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import logika.SpravceHry;

public class Okno extends JFrame {

	private static final long serialVersionUID = 1L;
		
	private SpravceHry spravce;
	private JButton[][] buttons = new JButton[10][10];
	private int pocetNestisknutych;
	
	
	public Okno(SpravceHry spravce) {
		this.spravce = spravce;
		initComponents();
	}
	
	private void initComponents() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Hledání min");
		pocetNestisknutych = 0;
		
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				buttons[j][i] = new JButton("");
				pocetNestisknutych++;
				buttons[j][i].putClientProperty("X", j);
				buttons[j][i].putClientProperty("Y", i);
				buttons[j][i].setBackground(Color.LIGHT_GRAY);
				if (spravce.getMiny()[j][i]) {
					buttons[j][i].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog((JButton)e.getSource(), "Šlápnul jsi na minu!", "Game Over", JOptionPane.ERROR_MESSAGE);
							zavriOkno();
						}
					});
				}
				else
					buttons[j][i].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JButton stisknute = (JButton)e.getSource();
							stiskniTlacitko((int)stisknute.getClientProperty("X"), (int)stisknute.getClientProperty("Y"), 0, 0);
							if (pocetNestisknutych - spravce.getPocetMin() == 0) {
								JOptionPane.showMessageDialog((JButton)e.getSource(), "Odhalil jsi všechny!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
								zavriOkno();
							}
						}
					});
				buttons[j][i].addMouseListener(new MouseAdapter() {
					 @Override
					 public void mouseClicked(MouseEvent e) {
						 if (SwingUtilities.isRightMouseButton(e)) {
							 JButton stisknute = (JButton)e.getSource();
							 if (stisknute.isEnabled() && stisknute.getBackground().equals(Color.LIGHT_GRAY)) {
								 stisknute.setEnabled(false);
								 stisknute.setBackground(Color.RED);
							 }
							 else if (!stisknute.isEnabled() && stisknute.getBackground().equals(Color.RED)) {
								 stisknute.setEnabled(true);
								 stisknute.setBackground(Color.LIGHT_GRAY);
							 }
						 }
					 }
				});
				add(buttons[j][i]);
			}
		}

		GridLayout mrizka = new GridLayout(10,10);
		setLayout(mrizka);
	}
	
	public void vykresliMiny(boolean[][] poleMin) {
		for(int i = 0; i < poleMin.length; i++) {
			for(int j = 0; j < poleMin[i].length; j++) {
				if (poleMin[j][i])
					buttons[j][i].setText("#");
			}
		}	
	}
	
	private void stiskniTlacitko(int x, int y, int smerX, int smerY) {
		int pocetOkolnich = spravce.zjistiPocetOkolnich(x, y);
		JButton stisknute = buttons[x][y];
		if (pocetOkolnich != 0)
			stisknute.setText(Integer.toString(pocetOkolnich));
		stisknute.setEnabled(false);
		stisknute.setBackground(Color.GRAY);
		pocetNestisknutych--;
		
		if (pocetOkolnich == 0) {
			if ((x - 1) >= 0 && smerX != 1 && buttons[x - 1][y].isEnabled())
				stiskniTlacitko(x - 1, y, -1, 0);
			if ((x + 1) < 10 && smerX != -1 && buttons[x + 1][y].isEnabled())
				stiskniTlacitko(x + 1, y, 1, 0);
			if ((y + 1) < 10 && smerY != -1 && buttons[x][y + 1].isEnabled())
				stiskniTlacitko(x, y + 1, 0, 1);
			if ((y - 1) >= 0 && smerY != -1 && buttons[x][y - 1].isEnabled())
				stiskniTlacitko(x, y - 1, 0, -1);
		}
	}
	
	public void zavriOkno() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
}
