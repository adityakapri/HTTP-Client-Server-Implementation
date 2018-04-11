package myServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyServerThread extends Thread {
	private Socket socket = null;
	
	//constructer of the class
	public MyServerThread(Socket socket) {
		super("MyServerThread");
		this.socket = socket;
	}
	public void run() {
		String serverResponse;
//		System.out.println("Inside run method In MyServerThread");
		try(
		   
		   PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));)
		{
//			System.out.println("AFTER READER WRITER INITIALIZED");
			String inputLine, outputLine;
			String requestHeader = "";
			String temp = null;
			int counter = 0;
			HttpImplementation httpImplementation= new HttpImplementation();
//			System.out.println("BEFORE WHILE LOOP");
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				temp = inputLine;
				requestHeader += temp + "\n";
				if(inputLine.contains("Mozilla/5.0")  ) //|| (inputLine.contains("/html"))
					break;
				 if(inputLine.contains("/html")  ) {
				    	 counter += 1;
				    }
				 if(counter == 2)
					 break;
			    }
			   
			System.out.println("AFTER while loop");
			System.out.println("My request Header is "+requestHeader);
			serverResponse = httpImplementation.processInput(requestHeader);
			System.out.println("Server Response From thread Class Before Writing to writer" +serverResponse);
			//write response to writer
			//*****************************uncomment this most important
			out.println(serverResponse);
			
			System.out.println("Closing connection");
			socket.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
