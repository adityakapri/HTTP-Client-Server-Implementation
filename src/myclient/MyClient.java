// HTTP client program
package myclient;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyClient {
	public static void main(String[] args) {
		//gets data entered  from command line
		//String hostName = args[0];
		//int portNumber = Integer.parseInt(args[1]);
		//String command = args[2];
		//String filePath = args[3];
		String hostName = "192.168.0.4";
		int portNumber = 8010; //443 for https
		String command = "GET";//GET  
		String filePath = "helloabhinav.html";//index.html hello.html

		if(command.equals("GET")) {
			createAndSendGetRequest(hostName,portNumber,filePath);
		}
		else if (command.equals("PUT")) {
			createAndSendPutRequest(hostName,portNumber,filePath);
		}
		else {
			System.out.println("My HTTP Client supports only GET AND PUT!");
		}
	}
	//	creates and send request for GET method to server
	//	read server response and display it

	private static void createAndSendGetRequest(String hostName,int portNumber, String filePath){
		//		declare socket and input output streams
		Socket socket            = null;
		PrintWriter clientRequest   = null;
		BufferedReader serverResponse     = null;
		try {
			//	    	creates TCP connection between client and server
			socket = new Socket(hostName, portNumber);	    		    	
			clientRequest = new PrintWriter(socket.getOutputStream(),true);
			serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			//       create and send  request 	   
			clientRequest.print("GET /" + filePath + "/ HTTP/1.1\r\n"); // "+path+"
			clientRequest.print("Host: " + hostName + "\r\n");
			clientRequest.print("Connection: close\r\n");
			clientRequest.print("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36\r\n");
			clientRequest.print("\r\n");
			clientRequest.flush();
			//        processing response from server
			String responseFromServer;
			while ((responseFromServer = serverResponse.readLine()) != null) {
				System.out.println(responseFromServer);
			} 	
			//			closes open connection and input output stream
			serverResponse.close();
			clientRequest.close();
			socket.close();
		}
		catch(UnknownHostException u){
			System.out.println(u);
		}
		catch (IOException i){
			System.out.println(i);
			System.out.println("SORRY CONNECTION COULD NOT BE ESTABLISHED");
		}
	}
	//	creates and send request for GET method to server
	//	read server response and display it
	private static void createAndSendPutRequest(String hostName,int portNumber, String fileName){
		//		declare socket and input output streams
		Socket socket            = null;
		PrintWriter clientRequest   = null;
		BufferedReader serverResponse     = null;
		try {
			//	    	creates TCP connection between client and server
			socket = new Socket(hostName, portNumber);	    		    	
			clientRequest = new PrintWriter(socket.getOutputStream(),true);
			serverResponse = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//	    	create and send post request
			//	    	PUT HTTP Header
			clientRequest.print("PUT /" + fileName + "/ HTTP/1.1\r\n"); 
			clientRequest.print("Host: " + hostName+"\r\n");
			clientRequest.print("Accept-Language: en-us\r\n");
			clientRequest.print("Connection: Keep-Alive\r\n");
			clientRequest.print("Mozilla/4.0 (compatible; MSIE5.01; Windows NT)\r\n");
			//	    	clientRequest.print("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36\r\n");
			//	    	clientRequest.print("Accept-Encoding: gzip, deflate\r\n");
			clientRequest.print("Content-type: text/html\r\n");
			clientRequest.print("Content-Length: 134\r\n");
			clientRequest.print("\r\n");
			clientRequest.print("BODY STARTS");
			//	    	PUT HTTP Body
			String fileContent = fileReader(fileName).toString();
			clientRequest.println(fileContent);
			clientRequest.flush();
			//	    processing response from server
			String responseFromServer;
			while ((responseFromServer = serverResponse.readLine()) != null) {
				System.out.println(responseFromServer);
			} 

			//		closes open connection and input output stream
			serverResponse.close();
			clientRequest.close();
			socket.close();
		}

		catch(UnknownHostException u){
			System.out.println(u);

		}
		catch (IOException i){
			System.out.println(i);
			System.out.println("SORRY CONNECTION COULD NOT BE ESTABLISHED");
		}

	}
	//    reads the content of the file to send to server
	//    stores it to a string
	private static StringBuffer fileReader(String fileName) {
		BufferedReader reader;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = null;
			String nextLine = System.getProperty("line.separator");

			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append(nextLine);
			}
			reader.close();


		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());						
		} catch (IOException e) {			
			System.out.println(e.getMessage());
		}
		return stringBuffer;
	}
}

