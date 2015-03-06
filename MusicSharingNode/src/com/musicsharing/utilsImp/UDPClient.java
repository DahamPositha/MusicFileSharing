package com.musicsharing.utilsImp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.musicsharing.utils.SocketClient;

public class UDPClient implements SocketClient {
	private final static int PACKETSIZE = 100;


	@Override
	public String callAndGotResponse(String server, int portNumber,
			String message) throws IOException {
		
		 DatagramSocket ds = new DatagramSocket();  
		    
		    InetAddress ip = InetAddress.getByName("localhost");  
		     
		    DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, 2000);  
		    ds.send(dp);  
		    ds.close();  
		
			return message;
	
		
	}
}