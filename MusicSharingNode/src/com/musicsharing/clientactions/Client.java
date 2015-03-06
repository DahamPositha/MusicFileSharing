package com.musicsharing.clientactions;

public interface Client {
	
	boolean registerAndJoinOverlay();
	
	String serviceTheReceivedMessage(String message);
	
	void searchFile(String prefixOfFile);
	
	void listenToNodes();
	
	
	

}
