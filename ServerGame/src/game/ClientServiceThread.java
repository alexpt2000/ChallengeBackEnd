package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map.Entry;

class ClientServiceThread extends Thread {
	Socket clientSocket;
	String message;
	int clientID = -1;
	boolean exit;
	int playControl = 0;
	GameState gameState = new GameState();
	
	String playerName[] = new String[2];
	
	ObjectOutputStream out;
	ObjectInputStream in;

	// Instance of Bean Classes
//	GameState gameState = new GameState();


	ClientServiceThread(Socket s, int i) {
		clientSocket = s;
		clientID = i;
	}

	// Method to Send MSG
	void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("client> " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());

		gameState.PopulateArray();
		
		try {
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());

			sendMessage("Connection successful\n----------------------\n \nWelcome to the 5-in-a-Row");

			boolean finish = false;
			boolean playerTurn = true;

			do {
				try {
					
					if (clientID > 1) {
						sendMessage("\nJust two players can conect at same time. Sorry.");
						break;
					}
					
					if(playerTurn){
						sendMessage("\nPlease, enter your name.: ");
						message = (String) in.readObject();
//						sendMessage(gameState.PrintGrid() + "\nIt's your turn " + message + " please enter column (1-99): ");
						playerName[0] = message;
						playerTurn = false;
					} else {
						
						if (gameState.checkResult("[X] ")) {
							sendMessage(gameState.PrintGrid() + "\n XXXXXXXX: ");
						} else if (gameState.checkResult("[O] ")){
							sendMessage(gameState.PrintGrid() + "\n OOOOOOOO: ");
						} else {
							sendMessage(gameState.PrintGrid() + "\nIt's your turn " + playerName[0] + " please enter column (1-9): ");
							message = (String) in.readObject();
							gameState.savePlayerChoice(message, playControl);
							playControl = playControl == 0 ? 1:0;
						}
					}
					
				} catch (ClassNotFoundException classnot) {
					System.err.println("Data received in unknown format");
				}

			} while (finish == false);

			System.out.println("Ending Player : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
