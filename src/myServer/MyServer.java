package myServer;

import java.io.IOException;
import java.net.ServerSocket;

public class MyServer {

	public static void main(String[] args) throws IOException {
		
////		unncomment later
//		if (args.length != 1) {
//	        System.out.println("Please enter PortNumber Only");
//	        System.exit(1);
//	    }
		
//		int portNumber = Integer.parseInt(args[0]);
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

