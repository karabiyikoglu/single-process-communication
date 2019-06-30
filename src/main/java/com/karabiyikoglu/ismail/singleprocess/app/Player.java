package com.karabiyikoglu.ismail.singleprocess.app;

import com.karabiyikoglu.ismail.singleprocess.app.constants.IConstants;

/**
 * Player class
 * @author ismail
 *
 */
public class Player implements Runnable {

	private String 			name;			//Player's name in order to distinguish from other players
	private MessageHandler 	messageHandler; //MessageHandler for sending and receiving message
	private int 			sentCount;		//Message sent count
	private int 			receivedCount;	//Message received count
	
	public Player(String name,MessageHandler messageHandler) {
		this.name 			= name;
		this.messageHandler = messageHandler;
		this.sentCount 		= 0;
		this.receivedCount 	= 0;
	}
	
	@Override
	public void run() {
		
		//At the begining we check if current player is initiator. 
		//If it is not, sleeps a little bit time for make initiator player own messageHandler's monitor
		if(messageHandler.getInitiatorPlayer() != this) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		communicate();
	}
	
	/**
	 * In this method current player sends and receives message to other player
	 */
	private void communicate() {
		
		StringBuilder newMessageBuilder = new StringBuilder();
		
		while(true) {
			synchronized (messageHandler) {//Only one player can use messageHandler at a time, the others must wait
				
				newMessageBuilder.setLength(0);//Clears newMessageBuilder content
				
				if(receivedCount >= IConstants.MAXIMUM_MESSAGE_COUNT && sentCount >= IConstants.MAXIMUM_MESSAGE_COUNT) {
					//Player reached the limit. We call notify method in case there is a waiting player thread 
					notifyOtherPlayer();
					break;
				}
				
				//Receive message
				receiveMessage(newMessageBuilder);
				
				
				if(receivedCount >= IConstants.MAXIMUM_MESSAGE_COUNT && sentCount >= IConstants.MAXIMUM_MESSAGE_COUNT) {
					//Player reached the limit. We call notify method in case there is a waiting player thread 
					notifyOtherPlayer();
					break;
				}
				
				//Reply message
				sendMessage(newMessageBuilder.toString());
				
				//Message has been sent, now notify other player 
				notifyOtherPlayer();
				
				waitForNewMessage();
			}
		}
	}

	/**
	 * Receives message from MessageHandler object
	 * @param newMessageBuilder
	 */
	private void receiveMessage(StringBuilder newMessageBuilder) {
		String receiveMessage = messageHandler.receiveMessage();//Receive a new message from messageHandler
		if(receiveMessage == null) {//No player has been sent any message
			newMessageBuilder.append(IConstants.DEFAULT_INIT_MESSAGE);//So, we start the conversation with initial message
		}else {
			//A message successfully received
			receivedCount++;
			if(IConstants.PRINT_MESSAGES_TO_CONSOLE) {
				System.out.println(name + " received message : " + receiveMessage);
			}
			newMessageBuilder.append(receiveMessage).append(" ").append(sentCount);
		}
	}
	
	/**
	 * Sends message to other player via MessageHandler object
	 * @param newMessage
	 */
	private void sendMessage(String newMessage) {
		if(IConstants.PRINT_MESSAGES_TO_CONSOLE) {
			System.out.println(name + " sent message     : " + newMessage);
		}
		messageHandler.setMessage(newMessage);
		sentCount++;
	}
	
	/**
	 * Waits until another thread invokes notify 
	 */
	private void waitForNewMessage() {
		try {
			messageHandler.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Wakes up another thread which is waiting on this messageHandler's monitor
	 */
	private void notifyOtherPlayer() {
		messageHandler.notify();
	}
}
