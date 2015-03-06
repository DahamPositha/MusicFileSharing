package com.musicsharing.utils;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface SocketClient {
	String callAndGotResponse(String server,int portNumber,String message) throws SocketException, UnknownHostException, IOException;

}
