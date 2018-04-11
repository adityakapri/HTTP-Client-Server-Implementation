package myclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;

import myServer.MyServerThread;

public class TestClass {
	public static void main(String[] args) throws IOException {
		int portNumber = 5001;
        boolean listening = true;
        try(ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
            		System.out.println("Server Waiting for connection request");
	            new MyServerThread(serverSocket.accept()).start();
	            System.out.println("One connection established");
	            
	        }
	    } catch (IOException e) {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
        	
        }
		
	}
	

