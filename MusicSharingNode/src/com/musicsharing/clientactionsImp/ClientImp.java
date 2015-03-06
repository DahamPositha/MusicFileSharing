package com.musicsharing.clientactionsImp;

// File Name GreetingClient.java

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.*;

import javax.swing.plaf.SliderUI;

import com.musicsharing.actionManagersImp.FileManagerImp;
import com.musicsharing.actionManagersImp.RegistrationManagerImp;
import com.musicsharing.actionManagersImp.RoutingTableManagerImp;
import com.musicsharing.actionManagersImp.WithinOverlayCommunicationManagerImp;
import com.musicsharing.clientactions.Client;
import com.musicsharing.dtos.TableRecord;
import com.musicsharing.globalitems.RoutingTableSingleton;
import com.musicsharing.utils.SocketServer;
import com.musicsharing.utilsImp.Constant;
import com.musicsharing.utilsImp.UDPServer;

public class ClientImp implements Client {
	RegistrationManagerImp registerManager;

	@Override
	public boolean registerAndJoinOverlay() {
		boolean isConnected=false;
		String server = Constant.server;
		int portNumber = Constant.portNumber;
		String myServer = Constant.myServer;
		int myPort = Constant.myServerPort;
		String myUserName = Constant.myUserName;
		registerManager = new RegistrationManagerImp();

		myUserName = Constant.myUserName;
		
		String outputString = registerManager.registerRequestAndGetResponse(
				server, portNumber, myServer, myPort, myUserName);
		String[] splited = outputString.split("\\s+");
		if(splited[1].equals("REGOK")){isConnected=true;}
		List<TableRecord> tr = tokanizeMessageAndGetRecords(outputString);

		Iterator<TableRecord> it = tr.iterator();
		System.out.println("Here are the table records");
		while (it.hasNext()) {
			TableRecord next = it.next();
			System.out.print(next.getServer() + " ");
			System.out.print(next.getPort() + " ");
			System.out.print(next.getUserName() + " ");
			System.out.println();
		}

		if (tr.size() > 0) {
			List<TableRecord> randomRecords = chooseTwoRandomTableRecords(tr);
			System.out.println("Two or One Random table records");
			Iterator<TableRecord> it2 = randomRecords.iterator();
			while (it2.hasNext()) {
				TableRecord next = it2.next();
				System.out.print(next.getServer() + " ");
				System.out.print(next.getPort() + " ");
				System.out.print(next.getUserName() + " ");
				System.out.println();
				new RoutingTableManagerImp().storeRoutingData(next.getServer(),
						next.getPort(), next.getUserName());
				new WithinOverlayCommunicationManagerImp().informTheJoining();
			}
		}
		return isConnected;

	}

	@Override
	public String serviceTheReceivedMessage(String message) {
		String[] splited = message.split("\\s+");
		if (splited[1].equals("SER")) {
			// length SER IP port file_name hops
			List<String> matchingFiles = new FileManagerImp()
					.getMatchingFiles(splited[4]);
			new WithinOverlayCommunicationManagerImp()
					.responseWithMatchingFiles(splited[2],
							Integer.parseInt(splited[3]), matchingFiles);
			new WithinOverlayCommunicationManagerImp().flooodTheMessage(
					splited[2], Integer.parseInt(splited[3]), splited[4],
					Integer.parseInt(splited[5]));
			
			
		} else if (splited[1].equals("JOIN")) {
			new RoutingTableManagerImp().storeRoutingData(splited[2],
					Integer.parseInt(splited[3]), "");

			
			
		} else if (splited[1].equals("LEAVE")) {
			new WithinOverlayCommunicationManagerImp().responseTheLeaving(
					splited[2], Integer.parseInt(splited[3]));

		} else {
		}
		return message;

	}

	private List<TableRecord> tokanizeMessageAndGetRecords(String message) {
		List<TableRecord> recordList = new ArrayList<TableRecord>();

		String[] splited = message.split("\\s+");
//		for (int i = 0; i < splited.length; i++) {
//			System.out.println(splited[i]);
//		}

		int numberOfPeers = 0;
		if (splited.length > 3) {
			numberOfPeers = Integer.parseInt(splited[2]);
		}
		if (numberOfPeers >= 1) {

			int messageTokenNumber = 3;

			for (int i = 0; i < numberOfPeers; i++) {
				TableRecord newRecord = new TableRecord();
				newRecord.setServer(splited[messageTokenNumber++]);
				newRecord.setPort(Integer
						.parseInt(splited[messageTokenNumber++]));
				newRecord.setUserName(splited[messageTokenNumber++]);
				recordList.add(newRecord);

			}
		}
		return recordList;

	}

	private List<TableRecord> chooseTwoRandomTableRecords(
			List<TableRecord> records) {
		List<TableRecord> inputRecords = records;
		List<TableRecord> outputRecords = new ArrayList<TableRecord>();
		int elementCount = records.size();
		System.out.println(elementCount);
		int randomRecord = 0;
		Random rand = new Random();
		if (elementCount == 1) {

			outputRecords.add(inputRecords.get(0));
			System.out.println("Rand 1: " + randomRecord);
			return outputRecords;
		} else {
			randomRecord = (int) Math.abs(rand.nextInt(elementCount - 1));
			outputRecords.add(inputRecords.get(randomRecord));
			System.out.println("Rand 1: " + randomRecord);
			randomRecord = (int) Math.abs(rand.nextInt(elementCount - 1));
			outputRecords.add(inputRecords.get(randomRecord));
			System.out.println("Rand 1: " + randomRecord);
			return outputRecords;
		}

	}

	@Override
	public void searchFile(String prefixOfFile) {
		new WithinOverlayCommunicationManagerImp().searchForMusicFile(prefixOfFile);
		
	}

	@Override
	public void listenToNodes() {
		SocketServer socketServer=new UDPServer();
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			try {
				socketServer.listenAndGotResponse(RoutingTableSingleton.getRoutingTable().getRecords().get(key).getServer(),RoutingTableSingleton.getRoutingTable().getRecords().get(key).getPort(),"");
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
		
	}
	}
}
