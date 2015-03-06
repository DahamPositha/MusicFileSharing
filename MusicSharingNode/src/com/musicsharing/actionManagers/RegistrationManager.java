package com.musicsharing.actionManagers;

public interface RegistrationManager {
	
	String registerRequestAndGetResponse(String server, int portNumber,String myServer,int myPort,String myUserName);

}
