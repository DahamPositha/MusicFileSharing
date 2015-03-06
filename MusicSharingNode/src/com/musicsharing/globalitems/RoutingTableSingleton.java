package com.musicsharing.globalitems;

import java.util.Hashtable;

import com.musicsharing.dtos.TableRecord;

public class RoutingTableSingleton {
	
	static RoutingTableSingleton routingTable;
	Hashtable<Integer, TableRecord> records;
	private RoutingTableSingleton(){
		
		records=new Hashtable<Integer,TableRecord>();
	}
	
	public  static RoutingTableSingleton getRoutingTable(){
		if(routingTable==null){
			routingTable=new RoutingTableSingleton();
		}
		return routingTable;
		
		
	}

	public Hashtable<Integer, TableRecord> getRecords() {
		return records;
	}

	public void setRecords(Hashtable<Integer, TableRecord> records) {
		this.records = records;
	}

}
