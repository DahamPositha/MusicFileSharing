package com.musicsharing.nodeLoop;

import java.io.IOException;

import com.musicsharing.actionManagersImp.FileManagerImp;
import com.musicsharing.clientactionsImp.ClientImp;
import com.musicsharing.globalitems.RoutingTableSingleton;
import com.musicsharing.utilsImp.Constant;
import com.musicsharing.utilsImp.SocketServerImp;

public class NodeLoop extends Thread {
	ClientImp client;

	public NodeLoop() {

		client = new ClientImp();
	}
	
	public void run(){
		while(true){
			//System.out.println("Listening...");
			ListeningFromServerSocket();
			
		}
		
	}

	public void initiateFiles() {

		new FileManagerImp().initiateFilesOfTheNode();
	}

	public void ListeningFromServerSocket() {
		new ClientImp().listenToNodes();
	}

	public boolean registerAndJoin() {

		return client.registerAndJoinOverlay();
	};

	public void searchFile(String prefixOfFile) {
		client.searchFile(prefixOfFile);

	}

	public static void main(String[] args) {
		NodeLoop nodeLoop = new NodeLoop();
		Thread listeningThread=new NodeLoop();
		listeningThread.start();
		nodeLoop.registerAndJoin();
	}
}
