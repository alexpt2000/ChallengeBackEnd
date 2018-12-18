package game;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Requester {

	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message = "";
	String clientResonse;
	String ipaddress;
	Scanner clientInput;


	Requester() {
	}

	void run() {
		clientInput = new Scanner(System.in);
		try {
			// 1. creating a socket to connect to the server
			ipaddress = "127.0.0.1"; // Local IP
			//ipaddress = "54.210.69.237"; // Remote IP

			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to " + ipaddress + " in port 2004");

			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());

			try {
				message = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			System.out.println(message);

			// 3: Communicating with the server
			do {
				try {

					// Receive MSG from the server
					message = (String) in.readObject();
					System.out.println(message);

					// Send MSG back
					clientResonse = clientInput.next(); // Input type by client
					sendMessage(clientResonse);

				} catch (ClassNotFoundException classNot) {
					System.err.println("data received in unknown format");
				}
			} while (!message.equals("exit"));

		} catch (UnknownHostException unknownHost) {
			System.err.println("You are trying to connect to an unknown host!");

		} catch (IOException ioException) {
			ioException.printStackTrace();

		} finally {
			// 4: Closing connection
			try {
				in.close();
				out.close();
				requestSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Requester client = new Requester();
		client.run();
	}
}