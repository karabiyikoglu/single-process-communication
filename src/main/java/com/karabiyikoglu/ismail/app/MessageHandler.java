package com.karabiyikoglu.ismail.app;

/**
 * MessageHandler class
 * @author ismail
 *
 */
public class MessageHandler {

	private String message;
	private Player initiatorPlayer;
	
	public synchronized void sendMessage(String message) {
		this.message = message;
		notify();
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized String receiveMessage() {
		return message;
	}

	public Player getInitiatorPlayer() {
		return initiatorPlayer;
	}

	public void setInitiatorPlayer(Player initiatorPlayer) {
		this.initiatorPlayer = initiatorPlayer;
	}
	
}
