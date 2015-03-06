package com.musicsharing.utilsImp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.musicsharing.utils.SocketClient;

public class SocketClientImp implements SocketClient {

	@Override
	public String callAndGotResponse(String server, int portNumber,
			String message) {
		String serverName = server;
		int port = portNumber;
		try {
			System.out.println("Connecting to " + serverName + " on port "
					+ port);
			Socket client = new Socket(serverName, port);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());

			PrintWriter output = new PrintWriter(client.getOutputStream(), true);
			InputStream in = client.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);//Set all input and output pipes
		
			output.print(message);
			output.flush();


			while (br.ready() == false) {

				//System.out.println("BR getting ready!");// Stop while buffered reader is getting ready
			}
			//System.out.println("Ok BR is ready");

			StringBuilder sb = new StringBuilder();
			int read;
			while ((read = br.read()) != -1) {

				sb.append((char) read);
			}
			String result = sb.toString();

			System.out.println("Server says " + result);

			output.close();
			in.close();
			// client.close();
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
