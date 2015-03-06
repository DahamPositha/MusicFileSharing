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

//	public static void main(String args[]) {
//		// Check the arguments
//		if (args.length != 2) {
//			System.out.println("usage: java DatagramClient host port");
//			return;
//		}
//
//		DatagramSocket socket = null;
//
//		try {
//			// Convert the arguments first, to ensure that they are valid
//			InetAddress host = InetAddress.getByName(args[0]);
//			int port = Integer.parseInt(args[1]);
//
//			// Construct the socket
//			socket = new DatagramSocket();
//
//			// Construct the datagram packet
//			byte[] data = "Hello Server".getBytes();
//			DatagramPacket packet = new DatagramPacket(data, data.length, host,
//					port);
//
//			// Send it
//			socket.send(packet);
//
//			// Set a receive timeout, 2000 milliseconds
//			socket.setSoTimeout(2000);
//
//			// Prepare the packet for receive
//			packet.setData(new byte[PACKETSIZE]);
//
//			// Wait for a response from the server
//			socket.receive(packet);
//
//			// Print the response
//			System.out.println(new String(packet.getData()));
//
//		} catch (Exception e) {
//			System.out.println(e);
//		} finally {
//			if (socket != null)
//				socket.close();
//		}
//	}

	@Override
	public String callAndGotResponse(String server, int portNumber,
			String message) throws IOException {
		
		 DatagramSocket ds = new DatagramSocket();  
		    String str = "Welcome java";  
		    InetAddress ip = InetAddress.getByName("localhost");  
		     
		    DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), ip, 2000);  
		    ds.send(dp);  
		    ds.close();  
		
			return message;
	
		
	}
}