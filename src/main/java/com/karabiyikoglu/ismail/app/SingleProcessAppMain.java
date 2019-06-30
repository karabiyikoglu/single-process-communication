package com.karabiyikoglu.ismail.app;

/**
 * Main class, application starts from here
 * @author ismail
 *
 */
public class SingleProcessAppMain {

	public static void main(String[] args) {
		
		MessageHandler messageHandler = new MessageHandler();
		
		//Create players
		//Every player must use same messageHandler object in order to communicate with each other
		Player playerOne = new Player("Player One",messageHandler);
		Player playerTwo = new Player("Player Two", messageHandler);
		
		//Define first sender player
		messageHandler.setInitiatorPlayer(playerOne);
		
		//Create threads for every player
		Thread playerOneThread = new Thread(playerOne);
		Thread playerTwoThread = new Thread(playerTwo);
		
		//Start every player's thread
		playerOneThread.start();
		playerTwoThread.start();
		
	}

}
