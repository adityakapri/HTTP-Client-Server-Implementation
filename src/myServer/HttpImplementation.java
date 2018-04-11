package myServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HttpImplementation {

	public String processInput(String requestHeader) throws IOException {
		String serverResponse = null  ;
		String temp = null;
//		System.out.println("Inside processInput method In HttpImplementation");
		StringBuffer sb = new StringBuffer();
		String file = requestHeader.split("\n")[0].split(" ")[1]
				.split("/")[1];
		System.out.println("Extracted file name    "+file);
		System.out.println("Extracted command"+requestHeader.split("\n")[0]);
		if(requestHeader.split("\n")[0].contains("GET")) //get command
		{
			System.out.println("Inside GET COMMAND BLOCK");
			if(validFile(file)) {
				System.out.println("file is valid");
				serverResponse = "200 OK" + "\n" + getFile(file);
				System.out.println(serverResponse);				
			}
			else {
				System.out.println("fie is invalid");
				serverResponse = "404 NOT FOUND";
			}
		}
		else if(requestHeader.split("\n")[0].contains("PUT"))//put command 
		{
//			System.out.println("Inside PUT COMMAND BLOCK");
			System.out.println("$$$$$$$$Request Header$$$$$$$$$$$$ " + requestHeader);
			String fileBody = requestHeader.split("BODY STARTS")[1];
			System.out.println("******* Html page********** " + fileBody );
			if (fileBody != "") {
				Boolean sucess  = saveFile(fileBody, file);
				if(sucess) {
					System.out.println("OK 200 File Created");
					serverResponse = "OK 200 File Created";
				}
				else {
					System.out.println("Unable to create file");
					serverResponse = "Unable to create file ,check if file already exists in your local directory";
				}
				}
		}
		else {
			System.out.println("My Server Support only GET AND PUT Command");
			
		}
		return serverResponse;
	}
	
	private Boolean saveFile(String fileBody, String file) throws IOException {
		String newFile = "server version:" +file;
		File myFile = new File(newFile);
		if(!myFile.exists()) {
			myFile.createNewFile();
		}
		else {
			System.out.println("File is already there");
			return false;	
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(myFile));
//			System.out.println("222222 "+fileBody);
			writer.write(fileBody);
			writer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
		
	}

	//if file present in local directory of server then return true else false 
	private boolean validFile(String file) {
		File myFile = new File(file);
		return myFile.exists() && !myFile.isDirectory();
	}
	
	private static String getFile(String file) {
		File myFile = new File(file);
		String fileContent = "";
		BufferedReader reader;		
		try {
			reader = new BufferedReader(new FileReader(myFile));
			String line = null;
			while (!(line = reader.readLine()).contains("</html>")) {
				fileContent += line;
			}
			fileContent += line;
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return fileContent;
	}

}
