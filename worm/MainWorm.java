package worm;

import javax.swing.*;

public class MainWorm {

	public static void main(String[] args) {
		int boardWidth = 500;
		int boardHeight = 500;
		
		//create window
		JFrame frame = new JFrame("Worm");
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight); //500 pixels by 500 pixels window
		frame.setLocationRelativeTo(null); //open window on center of the screen
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //prog close when user press x button
		
		WormGame wormGame = new WormGame(boardWidth, boardHeight);
		frame.add(wormGame);
		//run Program
		frame.pack(); //for full 500 pixels by 500 pixels dimensions
		wormGame.requestFocus(); //listen to key press
		
	}

}
