package game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.Scanner;

public class Client {
	private String host;
	private String playerName = null;
	private int port;
	private String clientResonse;
	String mark;
	String opponentMark;
	private String player = "";

//    PrintWriter out;
	Scanner clientInput;
	ObjectOutputStream out;
	ObjectInputStream in;

	public Client(String host, int ip) {
		this.host = host;
		this.port = ip;
	}

	public void onClientStart() throws IOException, ClassNotFoundException {
		connectToServer();
	}

	private void connectToServer() throws IOException, ClassNotFoundException {
		Socket socket = new Socket("localhost", 22222);
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(socket.getInputStream());
		clientInput = new Scanner(System.in);

		receiveFromServer();
	}

	private void receiveFromServer() throws IOException, ClassNotFoundException {

		String msg;

		while (true) {
			msg = (String) in.readObject();

			if (msg.startsWith("ACCEPT")) {
				System.out.println(msg);

			} else if (msg.startsWith("OPPONENT_MOVE")) {
				int i = Integer.valueOf(msg.substring(14));

			} else if (msg.startsWith("PLAYER1TURN")) {
				System.out.println(msg + "\nIt's your turn " + playerName + " please enter column (1-9): ");
				int choice = clientInput.nextInt();
				sendToServer("MOVE PLAYER1 " + choice);

			} else if (msg.startsWith("PLAYER2TURN")) {
				System.out.println(msg + "\nIt's your turn " + playerName + " please enter column (1-9): ");
				int choice = clientInput.nextInt();
				sendToServer("MOVE PLAYER2 " + choice);

			} else if (msg.startsWith("1PLAYER")) {
				System.out.println("Waiting for the player 1");

			} else if (msg.startsWith("2PLAYER")) {
				System.out.println("Waiting for the player 2");

			} else if (msg.startsWith("WINNER_PLAYER1")) {
				System.out.println("Player 1 it's the winner");

			} else if (msg.startsWith("WINNER_PLAYER1")) {
				System.out.println("Player 1 it's the winner");

			} else if (msg.startsWith("WAIT_PLAYER2")) {
				System.out.println(msg);

			} else if (msg.startsWith("PLAYER1")) {
				player = msg;

			} else if (msg.startsWith("PLAYER2")) {
				player = msg;

			} else if (msg.startsWith("INVALID_MESSAGE")) {
				System.out.println(msg);

			} else if (msg.equals("QUIT")) {
				return;
			}

		}
	}

	public void sendToServer(String message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
