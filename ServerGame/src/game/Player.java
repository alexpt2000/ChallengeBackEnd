package game;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;

public class Player extends Thread {
	private int gameId;
	private Socket socket;
	Player opponent;
	private GameState gameState;
	private String mark;
	private String playerName;
	private InetAddress inetAddress;
	ObjectOutputStream out;
	ObjectInputStream in;
	String playerTurn = "PLAYER1";

	public Player(Socket socket, GameState gameState, String mark, int gameId) {
		this.gameId = gameId;
		this.mark = mark;
		this.socket = socket;
		inetAddress = socket.getInetAddress();
		this.gameState = gameState;

		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socket.getInputStream());

			sendToClient("CONNECT");
			sendToClient("WAIT_PLAYER2");

			log("Player " + inetAddress.getHostName() + " PORT: " + socket.getPort() + " is connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String message = null;
			sendToClient("START_GAME");
			sendToClient("MARK " + mark + "\n" + gameState.PrintGrid());

			while (true) {

				if (playerTurn.equals("PLAYER1")) {
					sendToClient("PLAYER1TURN " + mark + "\n" + gameState.PrintGrid());
					playerTurn = "PLAYER2";
					sendToClient("2PLAYER");

				} else {
					sendToClient("PLAYER2TURN " + mark + "\n" + gameState.PrintGrid());
					playerTurn = "PLAYER1";
					sendToClient("1PLAYER");
				}

				message = (String) in.readObject();

				log("Game_id: " + gameId + " - " + message);

				if (message.startsWith("MOVE")) {
					int i = Integer.parseInt(message.substring(13));

					if (i < 0 || i > 9) {
						sendToClient("INVALID MOVE");
					} else {
						sendToClient("ACCEPT");

						if (gameState.checkResult("[X] ")) {
							sendToClient("WINNER_PLAYER1");
							System.out.println("Player 1 it's the winner");
						} else if (gameState.checkResult("[O] ")) {
							sendToClient("WINNER_PLAYER2");
							System.out.println("Player 2 it's the winner");
						} else {
							gameState.savePlayerChoice(i, message);
						}
					}

				} else if (message.equals("QUIT")) {
					log("PLAYER " + inetAddress.getHostName() + " PORT " + socket.getPort() + " DISCONNET");
					finalize();
				} else {
					sendToClient("BAD_REQUEST");
				}
			}

		} catch (IOException e) {
			opponent.sendToClient("OPONENT_DISCONECT");
			log("PLAYER " + inetAddress.getHostName() + " PORT " + socket.getPort() + " DISCONNET");
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	public void log(String message) {
		System.out.println(LocalTime.now() + " " + message);
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public void youStart() {
		sendToClient("PLAYER1");
	}

	public void opponentStart() {
		sendToClient("PLAYER2");
	}

	public void sendToClient(String message) {
		try {
			out.writeObject(message);
			out.flush();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
