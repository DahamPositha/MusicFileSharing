package com.musicsharing.actionManagersImp;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

import com.musicsharing.actionManagers.WithinOverlayCommunicationManager;
import com.musicsharing.globalitems.RoutingTableSingleton;
import com.musicsharing.utils.SocketClient;
import com.musicsharing.utilsImp.Constant;
import com.musicsharing.utilsImp.SocketClientImp;
import com.musicsharing.utilsImp.UDPClient;

public class WithinOverlayCommunicationManagerImp implements
		WithinOverlayCommunicationManager {

	/*
	 * request: length JOIN IP_address port_no e.g 0027 JOIN 64.12.123.190 432
	 * response: length JOINOK value e.g 0014 JOINOK 0
	 */
	@Override
	public void informTheJoining() {

		String messageSuffix = "";
		String fullMessage = "";

		messageSuffix += " JOIN " + Constant.myServer + " " + Constant.myServerPort;

		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println("Inform two nodes abount joining: "+fullMessage);
		SocketClient socketClient=new UDPClient();
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			try {
				socketClient.callAndGotResponse(
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getServer(),
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getPort(), fullMessage);
				System.out.println("inform to: "+RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getServer()+" "+
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/*
	 * request: length LEAVE IP_address port_no e.g 0028 LEAVE 64.12.123.190 432
	 * response: length LEAVEOK value e.g 0014 LEAVEOK 0
	 */
	@Override
	public void informTheLeaving() {
		String messageSuffix = "";
		String fullMessage = "";

		messageSuffix += " JOIN " + Constant.myServer + " " + Constant.myServerPort;

		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println(fullMessage);
		SocketClient socketClient=new UDPClient();
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			try {
				socketClient.callAndGotResponse(
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getServer(),
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getPort(), fullMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/*
	 * length SER IP port file_name hops e.g 0047 SER 129.82.62.142 5070
	 * "Lord of the rings"
	 */
	@Override
	public void flooodTheMessage(String server, int port, String fileName,
			int TTL) {

		String messageSuffix = "";
		String fullMessage = "";
		if (TTL != 0) {
			TTL--;
			messageSuffix += " SER " + server + " " + port + " " + fileName
					+ " " + TTL;
		}

		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println(fullMessage);
		SocketClient socketClient=new UDPClient();
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			try {
				socketClient.callAndGotResponse(
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getServer(),
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getPort(), fullMessage);
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

	/*
	 * length SEROK no_files IP port hops filename1 filename2 e.g 0114 SEROK 3
	 * 129.82.128.1 2301 baby_go_home.mp3 baby_come_back.mp3 baby.mpeg
	 */
	@Override
	public void responseWithMatchingFiles(String server, int port,
			List<String> files) {
		String messageSuffix = "";
		String fullMessage = "";

		messageSuffix += " SEROK " + files.size() + " " + Constant.myServer
				+ " " + Constant.myServerPort + " " + 0;
		Iterator<String> it = files.iterator();

		while (it.hasNext()) {
			String next = it.next();
			messageSuffix += " " + next;
		}

		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println(fullMessage);
		SocketClient socketClient=new UDPClient();
		if (files.size() != 0) {
			try {
				socketClient.callAndGotResponse(server, port, fullMessage);
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

	@Override
	public void responseTheLeaving(String server, int port) {
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			if (RoutingTableSingleton.getRoutingTable().getRecords().get(key)
					.getServer().equals(server)
					&& RoutingTableSingleton.getRoutingTable().getRecords()
							.get(key).getPort() == port) {

				RoutingTableSingleton.getRoutingTable().getRecords()
						.remove(key);
			}

		}

		String fullMessage = "0014 LEAVEOK 0";
		SocketClient socketClient=new UDPClient();
		try {
			socketClient.callAndGotResponse(server, port, fullMessage);
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

	@Override
	public void searchForMusicFile(String prefixOfMusic) {
		String messageSuffix = "";
		String fullMessage = "";

		messageSuffix += " SER "+ Constant.myServer
				+ " " + Constant.myServerPort + " "+prefixOfMusic+" " + Constant.TTL;
		
		double d = (double) (messageSuffix.length() + 4) / (double) 10000;

		fullMessage += String.format("%.4f", d).substring(2);
		fullMessage += messageSuffix;
		System.out.println(fullMessage);
		SocketClient socketClient=new UDPClient();
		for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
				.keySet()) {
			try {
				socketClient.callAndGotResponse(
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getServer(),
						RoutingTableSingleton.getRoutingTable().getRecords()
								.get(key).getPort(), fullMessage);
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
