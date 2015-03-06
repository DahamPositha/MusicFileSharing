package com.musicsharing.utilsImp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.musicsharing.clientactionsImp.ClientImp;
import com.musicsharing.utils.SocketServer;

public class UDPServer implements SocketServer {

	@Override
	public String listenAndGotResponse(String server, int portNumber,
			String message) throws SocketException, UnknownHostException,
			IOException {
		System.out.println("UDPServer is listening");
		InetAddress host=InetAddress.getByName(server);
		DatagramSocket ds = new DatagramSocket(portNumber, host); 
	    byte[] buf = new byte[1024];  
	    DatagramPacket dp = new DatagramPacket(buf, 1024);  
	    ds.receive(dp); 
	    String[] splited = dp.getData().toString().split("\\s+");
	    String receivedMessage = new String(dp.getData(), 0, Integer.parseInt(splited[0]));  
	    System.out.println("Message Received: "+receivedMessage);  
	    ds.close();  
	    new ClientImp().serviceTheReceivedMessage(receivedMessage);
	    
		return null;
	}

}
