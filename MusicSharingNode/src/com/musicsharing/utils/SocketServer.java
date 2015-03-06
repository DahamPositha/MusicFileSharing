package com.musicsharing.utils;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface SocketServer {
	String listenAndGotResponse(String server,int portNumber,String message) throws SocketException, UnknownHostException, IOException;

}
