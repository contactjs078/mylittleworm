package worm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //store segment of worms body
import java.util.Random; //random location of worm food

public class WormGame extends JPanel implements ActionListener, KeyListener { //inherit JPanel
	int boardWidth;
	int boardHeight;

	//move up!!
	int tileSize = 10;	
					
	//create class to track x and y positions of each tile
	//move up!!
	private class Tile{
		int x;
		int y;
						
		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {

		//wormHead
		g.setColor(Color.GRAY);
		g.fillRect(wormHead.x * tileSize, wormHead.y * tileSize, tileSize, tileSize);
	
		//wormBody
		for (int index = 0; index < wormBody.size(); index++) {
			Tile wormSegment = wormBody.get(index);
			g.fillRect(wormSegment.x * tileSize, wormSegment.y * tileSize, tileSize, tileSize);
		}
		
		//food
		g.setColor(Color.WHITE);
		g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
		
		//Grid
		for (int index = 0; index < boardWidth/tileSize; index++) {
			//Draws a line, using the current color, 
			//between the points (x1, y1) and (x2, y2)
			g.setColor(Color.YELLOW);
			g.drawLine(0, index*tileSize, boardWidth, index*tileSize); //draw horizontal lines
			g.drawLine(index*tileSize, 0, index*tileSize, boardHeight); //draw vertical lines
		}	// run program!!
		
		//worm length
		g.setFont(new Font("Calibri", Font.BOLD, 17));
		if(gameEnd){
			g.setColor(Color.DARK_GRAY);
			g.drawString("\"My little worm grew by " + 
			String.valueOf(wormBody.size()) + " segments.\"", 
			20, boardHeight-20);
		}
//		else {
//			g.drawString("\"My little worm grew by " + 
//			String.valueOf(wormBody.size()) + " segments.\"", 
//			tileSize + 20, tileSize + 50);
//		}
		
	}
	
	// run program!!
	
	//worm
	Tile wormHead;
	ArrayList<Tile> wormBody;

	//food
	Tile food;
	Random random;
	
	//logic
	Timer wormGameLoop;
	int velocityX;
	int velocityY;
	boolean gameEnd = false;
	
	WormGame(int boardWidth, int boardHeight){
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.ORANGE);
		addKeyListener(this);
		setFocusable(true); //listen to key press

		wormHead = new Tile(10, 10);
		wormBody = new ArrayList<Tile>();
		food = new Tile(10, 11);
		random = new Random();
		placeFood();
		wormGameLoop = new Timer(100, this);
		wormGameLoop.start();
		
		velocityX = 0;
		velocityY = 0;
	}
	
	public void placeFood() {
		food.x = random.nextInt(boardWidth/tileSize); //500/10 = 50
		food.y = random.nextInt(boardHeight/tileSize);		
	}
	
	//redraw frame
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		move();
		if (gameEnd) {
			wormGameLoop.stop();
		}
	}
	
	public void move() {
	
		//eat food
		if(collision(wormHead, food)) {
			wormBody.add(new Tile(food.x, food .y));
			placeFood();
		}
		
		//worm body
		for (int index = wormBody.size()-1; index >= 0; index --) {
			Tile wormPart = wormBody.get(index);
			if(index ==0) {
				wormPart.x = wormHead.x;
				wormPart.y = wormHead.y;
			}
			else {
				Tile previousWormPart = wormBody.get(index-1);
				wormPart.x = previousWormPart.x;
				wormPart.y = previousWormPart.y;
			}
		}
				
		//worm head
		wormHead.x += velocityX;
		wormHead.y += velocityY;
		
		//game end conditions
		for (int index =0; index < wormBody.size(); index++) {
			Tile wormPart = wormBody.get(index);
			//collide with the worm head
			if(collision(wormHead, wormPart)) {
				gameEnd = true;
			}
		}
		
		if(wormHead.x*tileSize < 0 || wormHead.x*tileSize > boardWidth ||
			wormHead.y*tileSize < 0 || wormHead.y*tileSize > boardHeight) {
			gameEnd= true;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;			
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;			
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;			
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;			
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public boolean collision(Tile tile1, Tile tile2) {
		return tile1.x == tile2.x && tile1.y == tile2.y; 
	}
	
}
