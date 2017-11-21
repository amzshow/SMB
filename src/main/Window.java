package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

	protected JFrame frame = new JFrame();
	
	public Window(int width, int height, int scale, String title, Game game) {
		
		frame.setTitle(title);
		frame.setPreferredSize(new Dimension(width * scale, height * scale));
		frame.setMaximumSize(new Dimension(width * scale, height * scale));
		frame.setMinimumSize(new Dimension(width * scale, height * scale));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}
	
	void setTitle(String title) {
		frame.setTitle(title);
	}
	
}
