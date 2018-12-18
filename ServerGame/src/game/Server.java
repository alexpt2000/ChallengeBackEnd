package game;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	private int PORT;

	public Server(int port) {
		this.PORT = port;
	}

	public void runServer() throws IOException {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server up....");
		while (true) {
			GameState gameState = new GameState();
			gameState.PopulateArray();

			Player first = new Player(serverSocket.accept(), gameState, "X", gameState.getGameId());
			gameState.setPlayer1(first);

			Player second = new Player(serverSocket.accept(), gameState, "O", gameState.getGameId());
			gameState.setPlayer2(second);

			first.setOpponent(second);
			second.setOpponent(first);

			first.start();
			second.start();
			gameState.startGame();

		}
	}
}
