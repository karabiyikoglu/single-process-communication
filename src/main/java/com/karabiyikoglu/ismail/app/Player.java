package com.karabiyikoglu.ismail.app;

import com.karabiyikoglu.ismail.app.constants.IApplicationConstants;

public class Player implements Runnable {

	private String 			name;
	private MessageHandler 	messageHandler;
	private int 			sentCount;
	private int 			receiveCount;
	
	public Player(String name,MessageHandler messageHandler) {
		this.name 			= name;
		this.messageHandler = messageHandler;
		this.sentCount 		= 0;
		this.receiveCount 	= 0;
	}
	
	@Override
	public void run() {
		
		if(messageHandler.getInitiatorPlayer() != this) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		communicate();
	}
	
	private void communicate() {
		
		StringBuilder newMessageBuilder = new StringBuilder();
		
		while(true) {
			synchronized (messageHandler) {//Only one player can use messageHandler at a time
				
				newMessageBuilder.setLength(0);//Clears newMessageBuilder content
				
				if(receiveCount >= IApplicationConstants.MAXIMUM_MESSAGE_COUNT && sentCount >= IApplicationConstants.MAXIMUM_MESSAGE_COUNT) {
					//Player reached the limit. We call notify method in case there is a waiting player thread 
					messageHandler.notify();
					break;
				}
				
				String receiveMessage = messageHandler.receiveMessage();//Receive a new message from messageHandler
				if(receiveMessage == null) {//No player has been sent any message
					newMessageBuilder.append(IApplicationConstants.DEFAULT_INIT_MESSAGE);
				}else {//A message successfully received
					receiveCount++;
					System.out.println(name + " received message : " + receiveMessage);
					newMessageBuilder.append(receiveMessage).append(" ").append(sentCount);
				}
				
				if(receiveCount >= IApplicationConstants.MAXIMUM_MESSAGE_COUNT && sentCount >= IApplicationConstants.MAXIMUM_MESSAGE_COUNT) {
					//Player reached the limit. We call notify method in case there is a waiting player thread 
					messageHandler.notify();
					break;
				}
				
				System.out.println(name + " sent message     : " + newMessageBuilder.toString());
				messageHandler.sendMessage(newMessageBuilder.toString());
				sentCount++;
			}
		}
	}
}
