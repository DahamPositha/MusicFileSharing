package com.musicsharing.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JList;

import com.musicsharing.dtos.TableRecord;
import com.musicsharing.globalitems.FileRepoSingleton;
import com.musicsharing.globalitems.RoutingTableSingleton;
import com.musicsharing.nodeLoop.NodeLoop;

public class MusicFinder extends JFrame {

	private JPanel contentPane;
	private JTextField searchField;
	private NodeLoop nodeLoop;
	JTextPane musicFilesField;
	JTextPane routingTableField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicFinder frame = new MusicFinder();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MusicFinder() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setToolTipText("Music File");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nodeLoop=new NodeLoop();
		
		JLabel lblNewLabel = new JLabel("Music File");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setToolTipText("Music File");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		searchField = new JTextField();
		searchField.setBounds(121, 8, 185, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nodeLoop.searchFile(searchField.getText());
			}
		});
		btnSearch.setBounds(325, 7, 89, 23);
		contentPane.add(btnSearch);
		
		JLabel lblSearchResults = new JLabel("Search Results");
		lblSearchResults.setBounds(10, 49, 80, 14);
		contentPane.add(lblSearchResults);
		
		JTextPane txtpnResults = new JTextPane();
		txtpnResults.setBounds(121, 43, 185, 20);
		contentPane.add(txtpnResults);
		
		
		
		JButton btnUnregister = new JButton("Unregister");
		btnUnregister.setBounds(283, 87, 89, 23);
		contentPane.add(btnUnregister);
		
		JLabel lblMusicList = new JLabel("Music List");
		lblMusicList.setBounds(10, 137, 46, 14);
		contentPane.add(lblMusicList);
		
		musicFilesField = new JTextPane();
		musicFilesField.setBounds(10, 162, 197, 88);
		contentPane.add(musicFilesField);
		
		JLabel lblNewLabel_1 = new JLabel("Routing Table");
		lblNewLabel_1.setBounds(217, 137, 89, 14);
		contentPane.add(lblNewLabel_1);
		
		routingTableField = new JTextPane();
		routingTableField.setBounds(217, 161, 197, 89);
		contentPane.add(routingTableField);
		
		JButton btnLoadMusicFiles = new JButton("Load Music Files");
		btnLoadMusicFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nodeLoop.initiateFiles();
			}
		});
		btnLoadMusicFiles.setBounds(10, 87, 109, 23);
		contentPane.add(btnLoadMusicFiles);
		
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				while(nodeLoop.registerAndJoin()==false){}
				
				List<String> musicList=FileRepoSingleton.getFileRepoSingleton().getMusicFiles();
				String musicString="";
				Iterator<String> it=musicList.iterator();
				while(it.hasNext()){
					
					musicString+=it.next()+",";
					
				}
				System.out.println(musicString);
				musicFilesField.setText(musicString);
				
				String routingData="";
				for (Integer key : RoutingTableSingleton.getRoutingTable().getRecords()
						.keySet()) {
					TableRecord next=RoutingTableSingleton.getRoutingTable().getRecords().get(key);
					routingData+=next.getServer()+",";
					routingData+=next.getPort()+",";
					routingData+=next.getUserName()+"\n";
				}
				System.out.println(routingData);
				routingTableField.setText(routingData);
			}
		});

		btnRegister.setBounds(162, 87, 89, 23);
		contentPane.add(btnRegister);
	}

	
	
}
