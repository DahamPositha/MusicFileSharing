package com.musicsharing.utilsImp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.musicsharing.clientactionsImp.ClientImp;
import com.musicsharing.utils.SocketServer;

public class SocketServerImp extends Thread implements SocketServer {
	private ServerSocket serverSocket;
	

	public SocketServerImp(int port) throws IOException{
		serverSocket = new ServerSocket(port);
		//serverSocket.setSoTimeout(10000);
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Server started and waiting for client on port "
						+ serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Just connected to "
						+ server.getRemoteSocketAddress());
				DataInputStream in = new DataInputStream(
						server.getInputStream());
				System.out.println(in.readUTF());
				StringBuilder sb=new StringBuilder();
				int read;
				while((read=in.read())>-1){
					
					sb.append((char)read);
				}
				String receivedMessage=sb.toString();
				
				new ClientImp().serviceTheReceivedMessage(receivedMessage);
				
				DataOutputStream out = new DataOutputStream(
						server.getOutputStream());
				out.writeUTF("Thank you for connecting to "
						+ server.getLocalSocketAddress() + "\nGoodbye!");
				server.close();
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	@Override
	public String listenAndGotResponse(String server, int portNumber,
			String message) throws SocketException, UnknownHostException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}
}
