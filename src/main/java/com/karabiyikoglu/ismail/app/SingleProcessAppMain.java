package com.karabiyikoglu.ismail.app;

/**
 * Main class, application starts from here
 * @author ismail
 *
 */
public class SingleProcessAppMain {

	public static void main(String[] args) {
		
		MessageHandler messageHandler = new MessageHandler();
		Player playerOne = new Player("Player One",messageHandler);
		Player playerTwo = new Player("Player Two", messageHandler);
		
		messageHandler.setInitiatorPlayer(playerOne);
		
		Thread playerOneThread = new Thread(playerOne);
		Thread playerTwoThread = new Thread(playerTwo);
		
		playerOneThread.start();
		playerTwoThread.start();
		
	}

}
