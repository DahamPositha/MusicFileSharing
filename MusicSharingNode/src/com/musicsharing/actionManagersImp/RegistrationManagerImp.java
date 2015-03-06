package com.musicsharing.actionManagersImp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import com.musicsharing.actionManagers.RegistrationManager;
import com.musicsharing.utils.SocketClient;
import com.musicsharing.utilsImp.SocketClientImp;
import com.musicsharing.utilsImp.UDPClient;

public class RegistrationManagerImp implements RegistrationManager {
	SocketClient client;

	@Override
	public String registerRequestAndGetResponse(String server, int portNumber,
			String myServer, int myPort, String myUserName) {
		client = new SocketClientImp();
		
		String message = createRegMessage(myServer, myPort, myUserName);
		
			try {
				return client.callAndGotResponse(server, portNumber, message);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		

	}

	private String createRegMessage(String myServer, int myPort,
			String myUserName) {

		String messageSuffix = "";
		String fullMessage = "";

		messageSuffix += " REG " + myServer + " " + myPort + " " + myUserName;

		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println("Registration Message: "+fullMessage);
		return fullMessage;

	}

}
