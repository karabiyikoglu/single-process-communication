package com.karabiyikoglu.ismail.singleprocess.app;

/**
 * MessageHandler class for sending and receiving message
 * @author ismail
 *
 */
public class MessageHandler {

	private String message;
	private Player initiatorPlayer;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String receiveMessage() {
		return message;
	}

	public Player getInitiatorPlayer() {
		return initiatorPlayer;
	}

	public void setInitiatorPlayer(Player initiatorPlayer) {
		this.initiatorPlayer = initiatorPlayer;
	}
	
}
